package shysao.com.azertyliste;

/**
 * Created by Alison on 21/04/2018.
 */

public class PlayerBean {
    int idplayer;
    String fnameP;
    String lnameP;
    String mailP;
    String lvlP;
    String hSignUPP;
    int StateP;
    String hrunP;
    String scoreP;

    public PlayerBean(int idplayer, String fnameP, String lnameP, String mailP, String lvlP, String hSignUPP, int stateP, String hrunP, String scoreP) {
        this.idplayer = idplayer;
        this.fnameP = fnameP;
        this.lnameP = lnameP;
        this.mailP = mailP;
        this.lvlP = lvlP;
        this.hSignUPP = hSignUPP;
        StateP = stateP;
        this.hrunP = hrunP;
        this.scoreP = scoreP;
    }

    public PlayerBean() {
    }

    public int getIdplayer() {
        return idplayer;
    }

    public String getFnameP() {
        return fnameP;
    }

    public String getLnameP() {
        return lnameP;
    }

    public String getMailP() {
        return mailP;
    }

    public String getLvlP() {
        return lvlP;
    }

    public String gethSignUPP() {
        return hSignUPP;
    }

    public int getStateP() {
        return StateP;
    }

    public String getHrunP() {
        return hrunP;
    }

    public String getScoreP() {
        return scoreP;
    }

    public void setIdplayer(int idplayer) {
        this.idplayer = idplayer;
    }

    public void setFnameP(String fnameP) {
        this.fnameP = fnameP;
    }

    public void setLnameP(String lnameP) {
        this.lnameP = lnameP;
    }

    public void setMailP(String mailP) {
        this.mailP = mailP;
    }

    public void setLvlP(String lvlP) {
        this.lvlP = lvlP;
    }

    public void sethSignUPP(String hSignUPP) {
        this.hSignUPP = hSignUPP;
    }

    public void setStateP(int stateP) {
        StateP = stateP;
    }

    public void setHrunP(String hrunP) {
        this.hrunP = hrunP;
    }

    public void setScoreP(String scoreP) {
        this.scoreP = scoreP;
    }
}

