# template-parser

A commandline client for parsing thymeleaf based templates.

# Example

```bash
export CLASSPATH=...
java de.sz.thymeleaf.cmd.Main \
  --template report.html \
  --out target/outfile.html \
  --data src/test/resources/data.csv \
  --static src/test/resources/static"
```

# Options

```
java de.sz.thymeleaf.cmd.Main --help
 --data DATEI      : a text/csv file, where data is read from, expected tab
                     separated with headlines
 --encoding WERT   : encoding for writer (Vorgabe: UTF-8)
 --locale WERT     : template engine locale (Vorgabe: en)
 --out DATEI       : write into this file
 --static DATEI    : directory with static resources, if set copy them next to
                     outfile
 --template WERT   : name of template from classpath to use
 --variables DATEI : properties file, where data is read from
 --zip             : if set, out is included with all required resources in a
                     file <out>.zip, not build (Vorgabe: false)
```

# Configuration

| key | description |
|:- |:- |
|CSVCONFIG.delimiter| default is ",". set Field delimiter to use, e.g. \u0009 for TAB |
|CSVCONFIG.variable_name | default is "D", set name under which data iterator should be available in template context |
