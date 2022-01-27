{ nixpkgs ? <nixpkgs>
, systems ? [ "x86_64-linux" ]
, LayoutModelJobset ? import ../LayoutModel/release.nix { inherit nixpkgs systems; }
}:

let
  pkgs = import nixpkgs {};
in
{
  build = pkgs.lib.genAttrs systems (system:
    let
      LayoutModel = builtins.getAttr system (LayoutModelJobset.build);
    in
    pkgs.stdenv.mkDerivation {
      name = "LayoutView";
      src = ./.;
      buildInputs = [ pkgs.ant pkgs.jdk8 ];

      LAYOUT_MODEL_LIB = "${LayoutModel}/share/java";
      TOMCAT_LIB = "${pkgs.tomcat9}/lib";

      buildPhase = ''
        ant generate.library.jar
        find . -name \*.class
      '';
      installPhase = ''
        mkdir -p $out/share/java
        mv *.jar $out/share/java
      '';
    });

  doc =
    let
      LayoutModel = builtins.getAttr (builtins.currentSystem) (LayoutModelJobset.build);
    in
    pkgs.stdenv.mkDerivation {
      name = "LayoutView-doc";
      src = ./.;
      buildInputs = [ pkgs.ant pkgs.jdk8 ];

      LAYOUT_MODEL_LIB = "${LayoutModel}/share/java";
      TOMCAT_LIB = "${pkgs.tomcat9}/lib";

      buildPhase = ''
        ant doc
      '';
      installPhase = ''
        mkdir -p $out
        mv doc $out
      '';
    };
}
