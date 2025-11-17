<#
.SYNOPSIS
Wrapper to run the template parser with a user friendly commandline in Powershell

.EXAMPLE
.\Run-Template-Parser.ps1 `
  -template wahr-falsch.html `
  -data .\wahr-falsch-aussagen.txt

#>

param(

    # file in sub directory templates to use
    [String]$template = "template.html",
    [String]$out = "target/out.html",
    [String]$variable = "variables.properties",

    # Tabel data is in this file
    [String]$data = "content.txt",

    # Directory with CSS und JS Resources
    [String]$static = "static",

    [Switch]$verbose
)

Set-Variable $CP="."
Get-ChildItem .\lib\*jar | ForEach-Object { $CP="$CP;$_" }

&${env:JAVA_HOME}\bin\java.exe -cp $CP de.sz.thymeleaf.cmd.Main `
  --template $template `
  --out $out `
  --variables $variable `
  --data $data `
  --static $static
