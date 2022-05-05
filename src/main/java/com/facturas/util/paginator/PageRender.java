package com.facturas.util.paginator;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@Getter
public class PageRender<T> {
    private String url;
    private Page<T> page;
    private List<PageItem> paginas;

    private int totalPaginas;

    private int numElementOfPage;
    private int pageActual;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PageItem>();

        numElementOfPage=page.getSize();
        totalPaginas=page.getTotalPages();
        pageActual=page.getNumber() + 1;


        int desde,hasta;

        if (totalPaginas <= numElementOfPage){
            desde = 1;
            hasta = totalPaginas;
        }else if(pageActual <= numElementOfPage/2){
            desde= 1;
            hasta=numElementOfPage;
        }else if (pageActual >= totalPaginas - numElementOfPage/2){
            desde = totalPaginas -numElementOfPage + 1;
            hasta = numElementOfPage;
        }else {
            desde = pageActual - numElementOfPage / 2;
            hasta = numElementOfPage;
        }

        for (int i = 0; i < hasta; i++) {
            paginas.add(new PageItem(desde + i, pageActual == desde+i));

        }


    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHaxNext(){
        return page.hasNext();
    }

    public boolean isHasPrevious(){
        return page.hasPrevious();
    }
}
