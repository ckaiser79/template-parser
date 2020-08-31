# template-parser

A commandline client for parsing thymeleaf based templates

# Example

```bash
export CLASSPATH=...
java de.sz.samples.thymeleaf.Main \
  --template report.html \
  --out target/outfile.html \
  --data src/test/resources/variables.properties \
  --static src/test/resources/static"
```
