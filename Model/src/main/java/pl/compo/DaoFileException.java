package pl.compo;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DaoFileException extends DaoException {

    private static Logger logger = LogManager.getLogger(DaoFileException.class.getName());

    public DaoFileException(String msg, Throwable c) {
        super(msg, c);
        logger.log(Level.ERROR, InternationalizationHandler.getMessage(msg));

    }
}
