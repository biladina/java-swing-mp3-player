package FileInfo;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import javazoom.jlgui.player.amp.tag.MpegInfo;
import javazoom.jlgui.player.amp.tag.TagInfoFactory;
import Entities.Song;

public class FileInfoPanel extends JTabbedPane implements LoadInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5547261444940624021L;
	private JPanel jPanelID3V1 = null;
	private JPanel pnlFileLocation = null;
	private JLabel lblFileLocation = null;

	private JPanel pnlMPEGInfo = null;
	private JLabel lblBitrate = null;
	private JLabel lblBitrateV = null;
	private JLabel lblChannels = null;
	private JLabel lblChannelsV = null;
	private JLabel lblChannelsMode = null;
	private JLabel lblChannelsModeV = null;
	private JLabel lblPalyingTime = null;
	private JLabel lblPlayingTimeV = null;
	private JLabel lblLayer = null;
	private JLabel lblLayerV = null;
	private JLabel lblSize = null;
	private JLabel lblSizeV = null;
	private JLabel lblCopyright = null;
	private JLabel lblCopyrightV = null;
	private JLabel lvlCrc = null;
	private JLabel lblCrcV = null;
	private JLabel lblOriginal = null;
	private JLabel lblOriginalV = null;
	private JLabel lblVbr = null;
	private JLabel lblVbrV = null;
	private JLabel lblEmphasis = null;
	private JLabel lblEmphasisV = null;

	private JPanel pnlID3V1 = null;
	private JPanel jPanelID3V2 = null;
	private JPanel jPanelID3V2LYRICS = null;
	
	/**
	 * This is the default constructor
	 */
	public FileInfoPanel() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(566, 328);
		this.addTab("ID3V1", null, getJPanelID3V1(), null);
		this.addTab("ID3V2", null, getJPanelID3V2(), null);
		this.addTab("ID3V2LYRICS", null, getJPanelID3V2LYRICS(), null);
	}
	
	/**
	 * This method initializes jPanelID3V1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelID3V1() {
		if (jPanelID3V1 == null) {
			jPanelID3V1 = new JPanel();
			jPanelID3V1.setLayout(null);
			jPanelID3V1.add(getPnlFileLocation(), null);
			jPanelID3V1.add(getPnlMPEGInfo(), null);
			jPanelID3V1.add(getPnlID3V1(), null);
		}
		return jPanelID3V1;
	}

	/**
	 * This method initializes pnlFileLocation	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlFileLocation() {
		if (pnlFileLocation == null) {
			lblFileLocation = new JLabel();
			lblFileLocation.setText("");
			lblFileLocation.setBounds(new java.awt.Rectangle(3,15,535,26));
			pnlFileLocation = new JPanel();
			pnlFileLocation.setLayout(null);
			pnlFileLocation.setBounds(new java.awt.Rectangle(6,6,540,48));
			pnlFileLocation.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "File location", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.SystemColor.activeCaption));
			pnlFileLocation.add(lblFileLocation, null);
		}
		return pnlFileLocation;
	}

	/**
	 * This method initializes pnlMPEGInfo	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlMPEGInfo() {
		if (pnlMPEGInfo == null) {
			lblEmphasisV = new JLabel();
			lblEmphasisV.setBounds(new java.awt.Rectangle(140,220,90,15));
			lblEmphasisV.setText("");
			lblEmphasis = new JLabel();
			lblEmphasis.setBounds(new java.awt.Rectangle(5,220,110,15));
			lblEmphasis.setForeground(java.awt.SystemColor.activeCaption);
			lblEmphasis.setText("Emphasis");
			lblVbrV = new JLabel();
			lblVbrV.setBounds(new java.awt.Rectangle(140,200,50,15));
			lblVbrV.setText("");
			lblVbr = new JLabel();
			lblVbr.setBounds(new java.awt.Rectangle(5,200,50,15));
			lblVbr.setForeground(java.awt.SystemColor.activeCaption);
			lblVbr.setText("VBR");
			lblOriginalV = new JLabel();
			lblOriginalV.setBounds(new java.awt.Rectangle(140,160,50,15));
			lblOriginalV.setText("");
			lblOriginal = new JLabel();
			lblOriginal.setBounds(new java.awt.Rectangle(5,160,60,15));
			lblOriginal.setForeground(java.awt.SystemColor.activeCaption);
			lblOriginal.setText("Original");
			lblCrcV = new JLabel();
			lblCrcV.setBounds(new java.awt.Rectangle(140,180,50,15));
			lblCrcV.setText("");
			lvlCrc = new JLabel();
			lvlCrc.setBounds(new java.awt.Rectangle(5,180,50,15));
			lvlCrc.setForeground(java.awt.SystemColor.activeCaption);
			lvlCrc.setText("CRC");
			lblCopyrightV = new JLabel();
			lblCopyrightV.setBounds(new java.awt.Rectangle(140,140,50,15));
			lblCopyrightV.setText("");
			lblCopyright = new JLabel();
			lblCopyright.setBounds(new java.awt.Rectangle(5,140,60,15));
			lblCopyright.setForeground(java.awt.SystemColor.activeCaption);
			lblCopyright.setText("Copyright");
			lblSizeV = new JLabel();
			lblSizeV.setBounds(new java.awt.Rectangle(140,120,90,15));
			lblSizeV.setText("");
			lblSize = new JLabel();
			lblSize.setBounds(new java.awt.Rectangle(5,120,85,15));
			lblSize.setForeground(java.awt.SystemColor.activeCaption);
			lblSize.setText("Size");
			lblLayerV = new JLabel();
			lblLayerV.setBounds(new java.awt.Rectangle(140,100,90,15));
			lblLayerV.setText("");
			lblLayer = new JLabel();
			lblLayer.setBounds(new java.awt.Rectangle(5,100,85,15));
			lblLayer.setForeground(java.awt.SystemColor.activeCaption);
			lblLayer.setText("Layer");
			lblPlayingTimeV = new JLabel();
			lblPlayingTimeV.setBounds(new java.awt.Rectangle(140,80,90,15));
			lblPlayingTimeV.setText("");
			lblPalyingTime = new JLabel();
			lblPalyingTime.setBounds(new java.awt.Rectangle(5,80,110, 15));
			lblPalyingTime.setForeground(java.awt.SystemColor.activeCaption);
			lblPalyingTime.setText("Playing time");
			lblChannelsModeV = new JLabel();
			lblChannelsModeV.setBounds(new java.awt.Rectangle(140,60,90,15));
			lblChannelsModeV.setText("");
			lblChannelsMode = new JLabel();
			lblChannelsMode.setBounds(new java.awt.Rectangle(5,60,110,15));
			lblChannelsMode.setForeground(java.awt.SystemColor.activeCaption);
			lblChannelsMode.setText("Channels mode");
			lblChannelsV = new JLabel();
			lblChannelsV.setBounds(new java.awt.Rectangle(140,40,90,15));
			lblChannelsV.setText("");
			lblChannels = new JLabel();
			lblChannels.setBounds(new java.awt.Rectangle(5,40,85,15));
			lblChannels.setForeground(java.awt.SystemColor.activeCaption);
			lblChannels.setText("Channels");
			lblBitrateV = new JLabel();
			lblBitrateV.setBounds(new java.awt.Rectangle(140,20,90,15));
			lblBitrateV.setText("");
			lblBitrate = new JLabel();
			lblBitrate.setBounds(new java.awt.Rectangle(5,20,85,15));
			lblBitrate.setForeground(java.awt.SystemColor.activeCaption);
			lblBitrate.setText("Bitrate");
			pnlMPEGInfo = new JPanel();
			pnlMPEGInfo.setLayout(null);
			pnlMPEGInfo.setBounds(new java.awt.Rectangle(7,53,239,241));
			pnlMPEGInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "MPEG Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.SystemColor.activeCaption));
			pnlMPEGInfo.add(lblBitrate, null);
			pnlMPEGInfo.add(lblBitrateV, null);
			pnlMPEGInfo.add(lblChannels, null);
			pnlMPEGInfo.add(lblChannelsV, null);
			pnlMPEGInfo.add(lblChannelsMode, null);
			pnlMPEGInfo.add(lblChannelsModeV, null);
			pnlMPEGInfo.add(lblPalyingTime, null);
			pnlMPEGInfo.add(lblPlayingTimeV, null);
			pnlMPEGInfo.add(lblLayer, null);
			pnlMPEGInfo.add(lblLayerV, null);
			pnlMPEGInfo.add(lblSize, null);
			pnlMPEGInfo.add(lblSizeV, null);
			pnlMPEGInfo.add(lblCopyright, null);
			pnlMPEGInfo.add(lblCopyrightV, null);
			pnlMPEGInfo.add(lvlCrc, null);
			pnlMPEGInfo.add(lblCrcV, null);
			pnlMPEGInfo.add(lblOriginal, null);
			pnlMPEGInfo.add(lblOriginalV, null);
			pnlMPEGInfo.add(lblVbr, null);
			pnlMPEGInfo.add(lblVbrV, null);
			pnlMPEGInfo.add(lblEmphasis, null);
			pnlMPEGInfo.add(lblEmphasisV, null);
		}
		return pnlMPEGInfo;
	}

	/**
	 * This method initializes pnlID3V1	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getPnlID3V1() {
		if (pnlID3V1 == null) {
			pnlID3V1 = new JPanel();
			pnlID3V1.setBounds(new java.awt.Rectangle(259,57,287,236));
			pnlID3V1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "ID3V1 Info", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12), java.awt.SystemColor.activeCaption));
		}
		return pnlID3V1;
	}

	/**
	 * This method initializes jPanelID3V2	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelID3V2() {
		if (jPanelID3V2 == null) {
			jPanelID3V2 = new JPanel();
		}
		return jPanelID3V2;
	}

	/**
	 * This method initializes jPanelID3V2LYRICS	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJPanelID3V2LYRICS() {
		if (jPanelID3V2LYRICS == null) {
			jPanelID3V2LYRICS = new JPanel();
		}
		return jPanelID3V2LYRICS;
	}
	
	/**
	 * Method that loads MPEG info and
	 * display it on panel
	 */
	public void LoadBasicInfo(Song s){
		if (s != null){
			System.out.println("Loading basic info");
			TagInfoFactory factory = TagInfoFactory.getInstance();
			MpegInfo tagInfo = (MpegInfo)factory.getTagInfo(s.getFilePath());
			
			lblFileLocation.setText(s.getFilePath());
			lblFileLocation.setToolTipText(s.getFilePath());
			
			lblBitrateV.setText("" + tagInfo.getBitRate());
			lblChannelsV.setText("" + tagInfo.getChannels());
			lblChannelsModeV.setText(tagInfo.getChannelsMode());
			lblLayerV.setText(tagInfo.getLayer());
			lblPlayingTimeV.setText("" + tagInfo.getPlayTime());
			lblSizeV.setText("" + tagInfo.getSize());
			lblCopyrightV.setText("" + tagInfo.getCopyright());
			lblCrcV.setText("" + tagInfo.getCRC());
			lblOriginalV.setText("" + tagInfo.getOriginal());
			lblVbrV.setText("" + tagInfo.getVBR());
			lblEmphasisV.setText(tagInfo.getEmphasis());
			
		}
	}


}  //  @jve:decl-index=0:visual-constraint="10,10"
