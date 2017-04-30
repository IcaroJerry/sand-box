package ifba.ads.ssol.memorymenager.gerenciadememoria;

import java.util.ArrayList;
import java.util.List;

public class PageMaker {

    public Pagina criarPagina() {
        page = new Pagina();
        return page;
    }

    public List<Pagina> criarPageList() {
        pageList = new ArrayList<Pagina>();
        return pageList;
    }

    public static PageMaker getInstance() {
        if (instance == null) {
            instance = new PageMaker();
        }
        return instance;
    }

    private static PageMaker instance = null;
    private Pagina page;
    private List<Pagina> pageList;
}
