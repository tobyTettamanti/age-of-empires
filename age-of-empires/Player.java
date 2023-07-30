package indy;


public class Player {

    private Country _country;
    private boolean _activeTurn;
    private int _income;

    public Player(Country country) {
        _activeTurn = false;
        _country = country;
        _income = 10;
    }

    public void setCountry(Country country) {
        _country = country;
    }

    public Country getCountry() {
        return _country;
    }

    public boolean isActiveTurn() {
        return _activeTurn;
    }

    public void setActiveTurn(boolean active) {
        _activeTurn = active;
    }

    public int getIncome() {
        return _income;
    }
    public void changeIncome (int amountChanged) {
        _income += amountChanged;
    }

    public String toString() {
        switch(_country) {
            case ABBASID_CALIPHATE: return "The Abbasid Caliphate";
            case HOLY_ROMAN_EMPIRE: return "The Holy Roman Empire";
            case SONG_DYNASTY: return "The Song Dynasty";
            case BYZANTINE_EMPIRE: return "The Byzantine Empire";
            default: return "";
        }
    }
}
