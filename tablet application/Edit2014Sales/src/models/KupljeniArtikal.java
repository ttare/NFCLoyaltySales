package models;

public class KupljeniArtikal {
	private Artikal kupljeniArtikal;
	int kolicina;
	
	public KupljeniArtikal(Artikal kupljeniArtikal, int kolicina) {
		super();
		this.kupljeniArtikal = kupljeniArtikal;
		this.kolicina = kolicina;
	}

	public Artikal getKupljeniArtikal() {
		return kupljeniArtikal;
	}

	public void setKupljeniArtikal(Artikal kupljeniArtikal) {
		this.kupljeniArtikal = kupljeniArtikal;
	}

	public int getKolicina() {
		return kolicina;
	}

	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	
	public float getIznos() {
		return kupljeniArtikal.getCijena() * kolicina;
	}
	
	public void povecajKolicinu() {
		kolicina += 1;
	}
	public void povecajKolicinu(int kol) {
		kolicina += kol;
	}	

}
