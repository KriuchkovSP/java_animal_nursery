package Animals;
public class AnimalsIsNotCreateException extends Exception{
    private String info;
    public AnimalsIsNotCreateException(int error, String message) {
        super(message);
        this.info = String.format("Код ошибки %d", error);
        if (((error >> 0) & 1) == 1) {
            this.info += "\r\nНе задано имя";
        }
    }

    public String getInfo() {return info;}
    
}
