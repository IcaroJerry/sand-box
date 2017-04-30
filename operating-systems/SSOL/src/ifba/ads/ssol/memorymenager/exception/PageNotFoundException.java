package ifba.ads.ssol.memorymenager.exception;

public class PageNotFoundException extends Exception {

    public String notFound() {
        return "pagina não encontrada";
    }
}
