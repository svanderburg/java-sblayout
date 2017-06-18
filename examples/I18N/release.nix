{ nixpkgs ? <nixpkgs>
, systems ? [ "x86_64-linux" ]
, LayoutModelJobset ? import ../../LayoutModel/release.nix { inherit nixpkgs systems; }
, LayoutViewJobset ? import ../../LayoutView/release.nix { inherit nixpkgs systems; }
}:

let
  pkgs = import nixpkgs {};
in
{
  build = pkgs.lib.genAttrs systems (system:
    let
      LayoutModel = builtins.getAttr system (LayoutModelJobset.build);
      LayoutView = builtins.getAttr system (LayoutViewJobset.build);
    in
    pkgs.stdenv.mkDerivation {
      name = "I18N";
      src = ./.;
      buildInputs = [ pkgs.ant pkgs.jdk ];
    
      LAYOUT_MODEL_LIB = "${LayoutModel}/share/java";
      LAYOUT_VIEW_LIB = "${LayoutView}/share/java";
      TOMCAT_LIB = "${pkgs.tomcat7}/lib";
      
      buildPhase = ''
        ant generate.war
      '';
      installPhase = ''
        mkdir -p $out/webapps
        mv *.war $out/webapps
      '';
    });
}
