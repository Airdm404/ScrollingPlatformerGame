package model.configuration;

/**
 * An exception class responsible for communicating file read failures during level creation.
 * As it extends from Exception, the exceptions should be caught in a try/catch block.
 * @author Mike Garay
 */
public class InvalidFileException extends Exception{
    private final ModelExceptionReason modelExceptionReason;

    /**
     * Constructs an InvalidFileException
     * @param modelExceptionReason The reason the file was found to be invalid
     * @param fileName The name of the file
     */
    public InvalidFileException(ModelExceptionReason modelExceptionReason, String fileName){
        super("File path was found to be invalid: " + fileName);
        this.modelExceptionReason = modelExceptionReason;
    }

    /**
     * Accessor for the exception reason of an InvalidConfigurationException
     * @return The ExceptionReason
     */
    public ModelExceptionReason getExceptionReason() {
        return this.modelExceptionReason;
    }
}
