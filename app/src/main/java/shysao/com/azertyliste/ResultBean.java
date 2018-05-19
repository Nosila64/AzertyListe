package shysao.com.azertyliste;

import java.util.ArrayList;

import shysao.com.azertyliste.PlayerBean;

/**
 * Created by Alison on 21/04/2018.
 */

public class ResultBean {
    private ArrayList<PlayerBean> results = new ArrayList<>();
    private int nbr;
    private ErrorBean error;

    public ResultBean(ArrayList<PlayerBean> results, int nbr, ErrorBean error) {
        this.results = results;
        this.nbr = nbr;
        this.error = error;
    }

    public ArrayList<PlayerBean> getResults() {
        return results;
    }

    public void setResults(ArrayList<PlayerBean> results) {
        this.results = results;
    }

    public int getNbr() {
        return nbr;
    }

    public void setNbr(int nbr) {
        this.nbr = nbr;
    }

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }
}
