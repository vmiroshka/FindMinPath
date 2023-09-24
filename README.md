There are numerous methods available for executing this application.

A substantial JAR file, named `Application.jar`, has been constructed using the SBT assembly plugin. This JAR can be initiated using the `scala` command.

By default, the application anticipates input data in a valid format from standard input (stdio). You can invoke the application by executing the following command:

```shell
cat {path_to_input_data} | scala {path_to_jar}
```

To streamline the input process and enable data reading from a file, a file reader has been implemented. However, it is worth noting that recompiling the project is necessary to utilize this feature.