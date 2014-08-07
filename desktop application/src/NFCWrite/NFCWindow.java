package NFCWrite;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import NDEF.NdefWriterUltralight;

public class NFCWindow extends JFrame{

	private String imeKupca;
	public String getImeKupca() {
		return imeKupca;
	}

	public void setImeKupca(String imeKupca) {
		this.imeKupca = imeKupca;
	}

	public String getPrezimeKupca() {
		return prezimeKupca;
	}

	public void setPrezimeKupca(String prezimeKupca) {
		this.prezimeKupca = prezimeKupca;
	}

	public String getDatumZadnjePromjene() {
		return datumZadnjePromjene;
	}

	public void setDatumZadnjePromjene(String datumZadnjePromjene) {
		this.datumZadnjePromjene = datumZadnjePromjene;
	}

	public String getUrlSlikeKupca() {
		return urlSlikeKupca;
	}

	public void setUrlSlikeKupca(String urlSlikeKupca) {
		this.urlSlikeKupca = urlSlikeKupca;
	}

	public String getBrojBodova() {
		return brojBodova;
	}

	public void setBrojBodova(String brojBodova) {
		this.brojBodova = brojBodova;
	}

	public String getBrojKartice() {
		return brojKartice;
	}

	public void setBrojKartice(String brojKartice) {
		this.brojKartice = brojKartice;
	}
	
	private JPanel contentPane;
	
	private String prezimeKupca;
	private String datumZadnjePromjene;
	private String urlSlikeKupca;
	private String brojBodova;
	private String brojKartice;
	public static String write;
	public NFCWindow() throws IOException {
final JTextField[] polja;
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 655, 536);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(0, 2, 0, 0));
		
		
		BufferedImage myPicture = null;
		myPicture = ImageIO.read(new File("Resources/logoNFC.jpg"));
		JLabel lblNewLabel = new JLabel(new ImageIcon(myPicture));
		
		FlowLayout fl_jpl = new FlowLayout();
		fl_jpl.setAlignment(FlowLayout.LEFT);
		fl_jpl.setAlignOnBaseline(true);
		JPanel jpl = new JPanel(fl_jpl);
		jpl.setAlignmentX(LEFT_ALIGNMENT);
		jpl.setSize(1000, 200);
		jpl.add(lblNewLabel);
		
		contentPane.add(jpl);
		lblNewLabel.setSize(200, 200);
		
		JPanel panel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		flowLayout.setAlignOnBaseline(true);
		contentPane.add(panel);
		
		JPanel fieldPanel = new JPanel(new GridLayout(14, 1));
        panel.add(fieldPanel, BorderLayout.CENTER);
        
        polja = new JTextField[6];
        for (int i = 0; i < 6; i += 1) {
        	polja[i] = new JTextField();
        	polja[i].setColumns(20);
        }
        polja[4].setEditable(false);
        polja[5].setEditable(false);
        polja[4].setText("0");
        Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy.");
        String date = DATE_FORMAT.format(today);
        polja[5].setText(date);
        
        JLabel naziv1 = new JLabel("  Broj kartice: ", JLabel.LEFT);
        naziv1.setLabelFor(polja[0]);
        fieldPanel.add(naziv1);
        JPanel p0 = new JPanel(new FlowLayout());
		p0.add(polja[0]);
		fieldPanel.add(p0);
        JLabel naziv2 = new JLabel("  Url slike: ", JLabel.LEFT);
        naziv1.setLabelFor(polja[1]);
        fieldPanel.add(naziv2);
        JPanel p1 = new JPanel(new FlowLayout());
		p1.add(polja[1]);
		fieldPanel.add(p1);
        JLabel naziv3 = new JLabel("  Ime: ", JLabel.LEFT);
        naziv1.setLabelFor(polja[2]);
        fieldPanel.add(naziv3);
        JPanel p2 = new JPanel(new FlowLayout());
		p2.add(polja[2]);
		fieldPanel.add(p2);
        JLabel naziv4 = new JLabel("  Prezime: ", JLabel.LEFT);
        naziv1.setLabelFor(polja[3]);
        fieldPanel.add(naziv4);
        JPanel p3 = new JPanel(new FlowLayout());
		p3.add(polja[3]);
		fieldPanel.add(p3);
        JLabel naziv5 = new JLabel("  Loyalty poeni: ", JLabel.LEFT);
        naziv1.setLabelFor(polja[4]);
        fieldPanel.add(naziv5);
        JPanel p4 = new JPanel(new FlowLayout());
		p4.add(polja[4]);
		fieldPanel.add(p4);
        final JLabel naziv6 = new JLabel("  Datum unosa: ", JLabel.LEFT);
        naziv1.setLabelFor(polja[5]);
        fieldPanel.add(naziv6);
        JPanel p5 = new JPanel(new FlowLayout());
		p5.add(polja[5]);
		fieldPanel.add(p5);
		JPanel p6 = new JPanel(new FlowLayout());
		JButton jButton = new JButton("Upiši na TAG");
        jButton.setSize(100, 20);
        p6.add(jButton);
		fieldPanel.add(p6);
		
		jButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				setBrojKartice(polja[0].getText().toString());
				setUrlSlikeKupca(polja[1].getText().toString());
				setImeKupca(polja[2].getText().toString());
				setPrezimeKupca(polja[3].getText().toString());        
		        write = getNewCardString();
		        try{
		        NDEF.NdefWriterUltralight.main(null);
		        JOptionPane.showMessageDialog(null,"Uspješno upisani podaci na tag.","Upis",JOptionPane.INFORMATION_MESSAGE);
		        for(int i=0; i<4; i++)
		        {
		        	polja[i].setText("");
		        }
		        }
		        catch(Exception e)
		        {
		        	JOptionPane.showMessageDialog(null,e.getMessage(),"Greška",JOptionPane.ERROR_MESSAGE);
		        }
			}
		});
		
		
	    }
	    
	private String getNewCardString() {
		Date today = new Date();
        SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd.MM.yyyy.");
        String date = DATE_FORMAT.format(today);
        this.datumZadnjePromjene = date;
		this.brojBodova = "0";
		String res = this.brojKartice + "*" + this.urlSlikeKupca + "*"
				+ this.imeKupca + "*" + this.prezimeKupca + "*"
				+ this.brojBodova + "*" + this.datumZadnjePromjene;
		return res;
	}
	
	    public static void main(String[] args) {
	        
	        SwingUtilities.invokeLater(new Runnable() {
	            @Override
	            public void run() {
	                NFCWindow ex = null;
					try {
						ex = new NFCWindow();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                ex.setVisible(true);
	            }
	        });
	    }
}
