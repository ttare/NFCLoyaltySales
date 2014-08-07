package models;

public class Artikal {

	private long id;
	private int barkod;
	private String naziv;
	private float cijena;
	private int kolicina;

	
	public Artikal(long id, int barkod, String naziv, float cijena, int kolicina) {
		super();
		this.id = id;
		this.barkod = barkod;
		this.naziv = naziv;
		this.cijena = cijena;
		this.kolicina = kolicina;
	}

	
	public Artikal() {
		// TODO Auto-generated constructor stub
	}



	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public int getBarkod() {
		return barkod;
	}
	public void setBarkod(int barkod) {
		this.barkod = barkod;
	}
	public String getNaziv() {
		return naziv;
	}
	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}
	public float getCijena() {
		return cijena;
	}
	public void setCijena(float cijena) {
		this.cijena = cijena;
	}
	
	@Override
	public String toString() {
		return "Artikal [id=" + id + ", barkod=" + barkod + ", naziv=" + naziv
				+ ", cijena=" + cijena + "]";
	}



	public int getKolicina() {
		return kolicina;
	}



	public void setKolicina(int kolicina) {
		this.kolicina = kolicina;
	}
	
	public void smanjiKolicinu(int kolicina) {
		this.kolicina -= kolicina;
	}
	
	public void smanjiKolicinu() {
		this.kolicina -= 1;
	}
}
