{ nixpkgs ? <nixpkgs>
, systems ? [ "x86_64-linux" ]
}:

let
  pkgs = import nixpkgs {};
in
{
  build = pkgs.lib.genAttrs systems (system:
    let
      pkgs = import nixpkgs { inherit system; };
    in
    pkgs.stdenv.mkDerivation {
      name = "LayoutModel";
      src = ./.;
      buildInputs = [ pkgs.ant pkgs.jdk8 ];
      buildPhase = ''
        ant generate.library.jar
      '';
      installPhase = ''
        mkdir -p $out/share/java
        mv *.jar $out/share/java
      '';
    });

  doc = pkgs.stdenv.mkDerivation {
    name = "LayoutModel-doc";
    src = ./.;
    buildInputs = [ pkgs.ant pkgs.jdk8 ];
    buildPhase = ''
      ant doc
    '';
    installPhase = ''
      mkdir -p $out
      mv doc $out
    '';
  };
}
