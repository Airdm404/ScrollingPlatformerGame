package model.configuration;

/**
 * An enum containing constants which each represent a reason
 * an exception was thrown in model
 * @author Mike Garay and Alex Lu
 */
public enum ModelExceptionReason {
    INVALID_CONFIG_KEY,
    INVALID_CONFIG_FILE,
    FILE_NOT_FOUND,
    DIRECTORY,
    NOT_A_FILE,
    NOT_A_CSV_FILE,
    ENTITY_CLASS_NOT_FOUND
}
