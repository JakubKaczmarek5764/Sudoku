package pl.compo;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DaoDatabaseException extends DaoException {
    private static Logger logger = LogManager.getLogger(DaoDatabaseException.class.getName());

    public DaoDatabaseException(String msg, Throwable c) {
        super(msg, c);
        logger.log(Level.ERROR, InternationalizationHandler.getMessage(msg));

    }
}
