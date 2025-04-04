import exception.DataAccessException;
import ui.Repl;

public class Main {
    public static void main(String[] args) throws DataAccessException {
        new Repl(8081).run();
    }
}