/*
 * PRINTDLGEX.java
 *
 * Created on 18. Mai 2007, 14:21
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package org.xvolks.jnative.misc;

import org.xvolks.jnative.exceptions.NativeException;
import org.xvolks.jnative.misc.basicStructures.AbstractBasicData;
import org.xvolks.jnative.misc.basicStructures.DC;
import org.xvolks.jnative.misc.basicStructures.DWORD;
import org.xvolks.jnative.misc.basicStructures.HWND;
import org.xvolks.jnative.misc.basicStructures.LONG;
import org.xvolks.jnative.pointers.NullPointer;
import org.xvolks.jnative.pointers.Pointer;
import org.xvolks.jnative.pointers.memory.GlobalMemoryBlock;
import org.xvolks.jnative.pointers.memory.MemoryBlockFactory;

/**
 *
 * @author Thubby
 *
 * Example Usage:
 *
 * PRINTDLGEX pPDX = new PRINTDLGEX();
 
 Pointer devModes = new Pointer(MemoryBlockFactory.createMemoryBlock(96));
 Pointer devNames = new Pointer(MemoryBlockFactory.createMemoryBlock(16));
 
 String printer = new String("HP LaserJet 2000");
 devModes.setStringAt(0, printer);
 devModes.setIntAt(36, devModes.getSize());
 devModes.setIntAt(40, PRINTDLGEX.DM_ORIENTATION | PRINTDLGEX.DM_PAPERSIZE);
 devModes.setIntAt(44, PRINTDLGEX.DMORIENT_LANDSCAPE & PRINTDLGEX.DMPAPER_LETTER);
 
 devNames.setShortAt(0, (short)8);
 devNames.setShortAt(2, (short)(8 + 1 + printer.length()));
 devNames.setShortAt(4, (short)(8 + 1 + 4));
 devNames.setShortAt(6, (short)0);
 
 GlobalMemoryBlock p_lngpDevMode = new GlobalMemoryBlock(96, GlobalMemoryBlock.GHND);
 GlobalMemoryBlock p_lngpDevNames = new GlobalMemoryBlock(16, GlobalMemoryBlock.GHND);
 
 pPDX.hDevMode = p_lngpDevMode.getPointer();
 pPDX.hDevNames = p_lngpDevNames.getPointer();
 
 p_lngpDevMode.copyMemory(devModes);
 p_lngpDevNames.copyMemory(devNames);
 
 pPDX.hwndOwner = User32.FindWindow(null,"Your window title here"); // MUST NOT BE NULL!!
 pPDX.Flags = new DWORD(PRINTDLGEX.PD_CURRENTPAGE | PRINTDLGEX.PD_NOSELECTION | PRINTDLGEX.PD_NOPAGENUMS);
 pPDX.nMinPage = new DWORD(0);
 pPDX.nMaxPage = new DWORD(1000);
 pPDX.nCopies = new DWORD(1);
 pPDX.nStartPage = new DWORD(PRINTDLGEX.START_PAGE_GENERAL);
 if(ComDlg32.PrintDlgEx(pPDX) != PRINTDLGEX.S_OK) {
 JNative.getLogger().log("Could not open PrintDlg");
 }
 
 JNative.getLogger().log(pPDX.getValueFromPointer().dwResultAction.getValue());
 
 p_lngpDevMode.dispose();
 p_lngpDevNames.dispose();
 */
public class PRINTDLGEX extends AbstractBasicData<PRINTDLGEX>
{
	
	public static final int PD_RESULT_CANCEL = 0;
	public static final int PD_RESULT_PRINT = 1;
	public static final int PD_RESULT_APPLY = 2;
	
	public static final int CCHDEVICENAME = 32;
	public static final int CCHFORMNAME = 32;
	public static final int PD_CURRENTPAGE = 0x400000;
	public static final int START_PAGE_GENERAL = 0xFFFFFFFF;
	public static final int S_OK = 0x0;
	
	// PRINTDLG_TYPE flags-Konstanten
	public static final int  PD_ALLPAGES = 0x0; // selektiert den "Alle Seiten"-OptionButton
	public static final int  PD_COLLATE = 0x10; // selektiert die "Sortieren"-Checkbox
	public static final int  PD_DISABLEPRINTTOFILE = 0x80000; //  "In Datei Drucken" deaktivieren
	public static final int  PD_ENABLEPRINTHOOK = 0x1000; // Hook-Prozedur soll benutzt werden
	public static final int  PD_ENABLEPRINTTEMPLATE = 0x4000; // benutzt Template-Namen
	public static final int  PD_ENABLEPRINTTEMPLATEHANDLE = 0x10000; // benutzt Template-Handles
	public static final int  PD_ENABLESETUPHOOK = 0x2000; // benutzt eine Hook-Prozedur für den Setup-Dialog
	public static final int  PD_ENABLESETUPTEMPLATE = 0x8000; // benutzt einen Template-Namen für den
	// Setup-Dialog
	public static final int  PD_ENABLESETUPTEMPLATEHANDLE = 0x20000; // benutzt ein Template-Handle _
	// für den Setup-Dialog
	public static final int  PD_HIDEPRINTTOFILE = 0x100000; //  "In Datei Drucken"-Button verstecken
	public static final int  PD_NONETWORKBUTTON = 0x200000; // Netzwerk -Button verstecken
	public static final int  PD_NOPAGENUMS = 0x8; //  "Von Bis" ist deaktiviert
	public static final int  PD_NOSELECTION = 0x4; //  "Auswahl" ist deaktiviert
	public static final int  PD_NOWARNING = 0x80; // keine Warnungen wenn der gewählte Drucker nicht
	// der Standard-Drucker ist
	public static final int  PD_PAGENUMS = 0x2; // selektiert den "Von Bis"-OptionButton
	public static final int  PD_PRINTSETUP = 0x40; // öffnet den Drucker-Setup-Dialog
	public static final int  PD_PRINTTOFILE = 0x20; // selektiert die "In Datei Drucken"-Checkbox
	public static final int  PD_RETURNDC = 0x100; // gibt einen Device-Kontext des Druckers zurück
	public static final int  PD_RETURNDEFAULT = 0x400; // lädt den Standard-Drucker, dazu müssen bei
	// hDevMode und hDevNames eine "0" übergeben werden
	public static final int  PD_RETURNIC = 0x200; // gibt einen Informations-Kontext zu dem gewählten
	// Drucker als hDC zurück
	public static final int  PD_SELECTION = 0x1; // selektiert die "Auswahl"-Option
	public static final int  PD_SHOWHELP = 0x800; // Zeigt den Hilfe-Button an
	public static final int  PD_USEDEVMODECOPIESANDCOLLATE = 0x40000; // berücksichtigt
	// Geräte-Einschränkungen und gibt nur mögliche Werte der Kopien oder des Sortierens zurück
	
	// DEVMODE dmFields-Konstanten
	public static final int  DM_ORIENTATION = 0x1; // dmOrientation wird gefüllt/gelesen
	public static final int  DM_PAPERSIZE = 0x2; // dmPaperSize wird gefüllt/gelesen
	public static final int  DM_PAPERLENGTH = 0x4; // dmPaperLenght wird gefüllt/gelesen
	public static final int  DM_PAPERWIDTH = 0x8; // dmPaperWidth wird gefüllt/gelesen
	public static final int  DM_SCALE = 0x10; // dmScale wird gefüllt/gelesen
	public static final int  DM_COPIES = 0x100; // dmCopies wird gefüllt/gelesen
	public static final int  DM_DEFAULTSOURCE = 0x200; // dmDefaultSource wird gefüllt/gelesen
	public static final int  DM_PRINTQUALITY = 0x400; // dmPrintQuality wird gefüllt/gelesen
	public static final int  DM_COLOR = 0x800; // dmColor wird gefüllt/gelesen
	public static final int  DM_DUPLEX = 0x1000; // dmDuplex wird gefüllt/gelesen
	public static final int  DM_YRESOLUTION = 0x2000; // dmYResolution wird gefüllt/gelesen
	public static final int  DM_TTOPTION = 0x4000; // dmTTOption wird gefüllt/gelesen
	public static final int  DM_COLLATE = 0x8000; // dmCollate wird gefüllt/gelesen
	public static final int  DM_FORMNAME = 0x10000; // dmFormName wird gefüllt/gelesen
	public static final int  DM_LOGPIXELS = 0x20000; // dmLogPixels wird gefüllt/gelesen
	public static final int  DM_BITSPERPEL = 0x40000; // dmBitsPerPixel wird gefüllt/gelesen
	public static final int  DM_PELSWIDTH = 0x80000; // dmPelsWidth wird gefüllt/gelesen
	public static final int  DM_PELSHEIGHT = 0x100000; // dmPelsHeight wird gefüllt/gelesen
	public static final int  DM_DISPLAYFLAGS = 0x200000; // dmDisplayFlags wird gefüllt/gelesen
	public static final int  DM_DISPLAYFREQUENCY = 0x400000; // dmDisplayFrequency wird gefüllt/gelesen
	public static final int  DM_ICMMETHOD = 0x800000; // dmICMMethod wird gefüllt/gelesen
	public static final int  DM_ICMINTENT = 0x1000000; // dmICMIntent wird gefüllt/gelesen
	public static final int  DM_MEDIATYPE = 0x2000000; // dmMediaType wird gefüllt/gelesen
	public static final int  DM_DITHERTYPE = 0x4000000; // dmDitherType wird gefüllt/gelesen
	public static final int  DM_PANNINGWIDTH = 0x20000000; // dmPanningWidth wird gefüllt/gelesen
	public static final int  DM_PANNINGHEIGHT = 0x40000000; // dmPanningHeight wird gefüllt/gelesen
	
	// DEVMODE dmOrientation-Konstanten
	public static final int  DMORIENT_PORTRAIT = 1; // Portrait wurde gewählt (vertikal Drucken)
	public static final int  DMORIENT_LANDSCAPE = 2; // Landscape wurde gewählt (horizontal Drucken)
	
	// DEVMODE dmPaperSize-Konstanten
	public static final int  DMPAPER_LETTER = 1; // Blatt 8,5 x 11 Inch
	public static final int  DMPAPER_LEGAL = 5; // Blatt 8,5 x 14 Inch
	public static final int  DMPAPER_10X11 = 45; // 10 x 11 Inch
	public static final int  DMPAPER_10X14 = 16; // 10 x 14 Inch
	public static final int  DMPAPER_11X17 = 17; // 11 x 17 Inch
	public static final int  DMPAPER_15X11 = 46; // 15 x 11 Inch
	public static final int  DMPAPER_9X11 = 44; // 9 x 11 Inch
	public static final int  DMPAPER_A_PLUS = 57; // A Plus Blatt
	public static final int  DMPAPER_A2 = 66; // DIN A2 Blatt
	public static final int  DMPAPER_A3 = 8; // DIN A3 Blatt
	public static final int  DMPAPER_A3_EXTRA = 63; // DIN A3 Extra Blatt
	public static final int  DMPAPER_A3_EXTRA_TRANSVERSE = 68; // DIN A3 Extra Blatt querlaufend
	public static final int  DMPAPER_A3_TRANSVERSE = 67; // DIN A3 Blatt querlaufend
	public static final int  DMPAPER_A4 = 9; // DIN A4 Blatt
	public static final int  DMPAPER_A4_EXTRA = 53; // DIN A4 Extra Blatt
	public static final int  DMPAPER_A4_PLUS = 60; // DIN A4 Plus Blatt
	public static final int  DMPAPER_A4_TRANSVERSE = 55; // DIN A4 querlaufend
	public static final int  DMPAPER_A4SMALL = 10; // DIN A4 klein (210 x 297 Millimeter) Blatt
	public static final int  DMPAPER_A5 = 11; // DIN A5 Blatt
	public static final int  DMPAPER_A5_EXTRA = 64; // DIN A5 Extra Blatt
	public static final int  DMPAPER_A5_TRANSVERSE = 61; // DIN A5 querlaufend
	public static final int  DMPAPER_B_PLUS = 58; // B Plus Blatt
	public static final int  DMPAPER_B4 = 12; // B4 Blatt
	public static final int  DMPAPER_B5 = 13; // B5 Blatt
	public static final int  DMPAPER_B5_EXTRA = 65; // B5 Extra Blatt
	public static final int  DMPAPER_B5_TRANSVERSE = 62; // B5 Blatt querlaufend
	public static final int  DMPAPER_CSHEET = 24; // C Blatt (17 x 22 Inch)
	public static final int  DMPAPER_DSHEET = 25; // D Blatt (22x34 Inch)
	public static final int  DMPAPER_ENV_10 = 20; // Briefumschlag 10 (4,125 x 9,5 Inch)
	public static final int  DMPAPER_ENV_11 = 21; // Briefumschlag 11 (4,5 x 10,375 Inch)
	public static final int  DMPAPER_ENV_12 = 22; // Briefumschlag 12 (4,75 x 11 Inch)
	public static final int  DMPAPER_ENV_14 = 23; // Briefumschlag 14 (5 x 11,5 Inch)
	public static final int  DMPAPER_ENV_9 = 19; // Briefumschlag 9 (3,875 x 8,875 Inch)
	public static final int  DMPAPER_ENV_B4 = 33; // Briefumschlag B4 (250 x 353 Millimeter)
	public static final int  DMPAPER_ENV_B5 = 34; // Briefumschlag B5 (176 x 250 Millimeter)
	public static final int  DMPAPER_ENV_B6 = 35; // Briefumschlag B6 (176 x 125 Millimeter)
	public static final int  DMPAPER_ENV_C3 = 29; // Briefumschlag C3 (324 x 458 Millimeter)
	public static final int  DMPAPER_ENV_C4 = 30; // Briefumschlag C4 (229 x 324 Millimeter)
	public static final int  DMPAPER_ENV_C5 = 28; // Briefumschlag C5 (162 x 229 Millimeter)
	public static final int  DMPAPER_ENV_C6 = 31; // Briefumschlag C6 (114 x 162 Millimeter)
	public static final int  DMPAPER_ENV_C65 = 32; // Briefumschlag 10 (4,125 x 9,5 Inch)
	public static final int  DMPAPER_ENV_DL = 27; // Briefumschlag 10 (4,125 x 9,5 Inch)
	public static final int  DMPAPER_ENV_INVITE = 47; // Einladungs Briefumschlag
	public static final int  DMPAPER_ENV_ITALY = 36; // Italienischer Briefumschlag (110 x 230 Millimeter)
	public static final int  DMPAPER_ENV_MONARCH = 37; // Monarchischer Briefumschlag (3,875 x 7,5; // Inch)
	public static final int  DMPAPER_ENV_PERSONAL = 38; // Persönlicher Briefumschlag (3,625 x 6,5; // Inch)
	public static final int  DMPAPER_ESHEET = 26; // E Blatt (34 x 44 Inch)
	public static final int  DMPAPER_EXECUTIVE = 7; // Executive Blatt (7.25 x 10,5 Inch)
	public static final int  DMPAPER_FANFOLD_LGL_GERMAN = 41; // Deutscher Rechtlicher Fanfold (8,5 x
	// 13 Inch)
	public static final int  DMPAPER_FANFOLD_STD_GERMAN = 40; // Deutscher Standard Fanfold (8,5 x 12; // Inch)
	public static final int  DMPAPER_FANFOLD_US = 39; // US Standard Fanfold (14,875 + 11 Inch)
	public static final int  DMPAPER_FIRST = 1; // Blatt 8,5 x 11 Inch
	public static final int  DMPAPER_FOLIO = 14; // Folin 8,5 + 13 Inch
	public static final int  DMPAPER_ISO_B4 = 42; // ISO B4 Blatt
	public static final int  DMPAPER_JAPANESE_POSTCARD = 43; // Japanische Postkarte
	public static final int  DMPAPER_LAST = 41; // Deutscher Rechtlicher Fanfold (8,5 x 13 Inch)
	public static final int  DMPAPER_LEDGER = 4; // Ledger (17x11 Inch)
	public static final int  DMPAPER_LEGAL_EXTRA = 51; // Rechtlich Extra
	public static final int  DMPAPER_LETTER_EXTRA = 50; // Blatt Extra
	public static final int  DMPAPER_LETTER_EXTRA_TRANSVERSE = 56; // Blatt Extra querlaufend
	public static final int  DMPAPER_LETTER_PLUS = 59; // Blatt Plus
	public static final int  DMPAPER_LETTER_TRANSVERSE = 54; // Blatt querlaufend
	public static final int  DMPAPER_LETTERSMALL = 2; // Blatt klein (8,5 x 11 Inch)
	public static final int  DMPAPER_NOTE = 18; // Note Blatt (8,5 x 11 Inch)
	public static final int  DMPAPER_QUARTO = 15; // Quarto Blatt (215 x 275 Millimeter)
	public static final int  DMPAPER_STATEMENT = 6; // Statement Blatt (5,5 x 8,5 Inch)
	public static final int  DMPAPER_TABLOID = 3; // Tabloid Blatt (11 x 17 Inch)
	public static final int  DMPAPER_TABLOID_EXTRA = 52; // Tabloid Extra Blatt
	public static final int  DMPAPER_USER = 256; // benutzerdefinierte Größe
	
	// DEVMODE dmDefaultSource-Konstanten
	public static final int  DMBIN_ONLYONE = 1; // nur ein Blatt
	public static final int  DMBIN_UPPER = 1; // Behälter Oben
	public static final int  DMBIN_LOWER = 2; // Behälter Unten
	public static final int  DMBIN_MIDDLE = 3; // Behälter Mitte
	public static final int  DMBIN_MANUAL = 4; // Behälter mit manueller Füllung
	public static final int  DMBIN_ENVELOPE = 5; // Briefumschlag Behälter
	public static final int  DMBIN_ENVMANUAL = 6; // Briefumschlag Behälter mit Manueller Füllung
	public static final int  DMBIN_AUTO = 7; // automatisches füllen
	public static final int  DMBIN_TRACTOR = 8; // Blatt Einzug
	public static final int  DMBIN_SMALLFMT = 9; // kleines Format laden
	public static final int  DMBIN_LARGEFMT = 10; // großes Format laden
	public static final int  DMBIN_LARGECAPACITY = 11; // große Kapazität
	public static final int  DMBIN_CASSETTE = 14; // Blatt Kassette
	public static final int  DMBIN_FORMSOURCE = 15; // Form Papier Quelle
	
	// DEVMODE dmPrintQuality-Konstanten
	public static final int  DMRES_DRAFT = -1; // Entwurf
	public static final int  DMRES_LOW = -2; //  niedrig
	public static final int  DMRES_MEDIUM = -3; // mittel
	public static final int  DMRES_HIGH = -4; // hoch
	
	// DEVMODE dmColor-Konstanten
	public static final int  DMCOLOR_MONOCHROME = 1; // Gerät unterstützt keine Schwarzweißausgabe
	public static final int  DMCOLOR_COLOR = 2; // Gerät unterstützt keine Farbausgabe
	
	// DEVMODE dmDuplex-Konstanten
	public static final int  DMDUP_SIMPLEX = 1; // einseitiges Drucken
	public static final int  DMDUP_VERTICAL = 2; // beidseitig Drucken bei vertikalem Seitenwechsel
	public static final int  DMDUP_HORIZONTAL = 3; // beidseitig Drucken bei horizontalem Seitenwechsel
	
	// DEVMODE dmTTOption-Konstanten
	public static final int  DMTT_BITMAP = 1; // druckt TrueType-Fonts als Grafiken
	public static final int  DMTT_DOWNLOAD = 2; // druckt Downloaded TrueType-Fonts als Soft-Fonts
	public static final int  DMTT_SUBDEV = 4; // druckt Geräte-Fonts statt TrueType-Fonts
	
	// DEVMODE dmCollate-Konstanten
	public static final int  DMCOLLATE_FALSE = 0; // Drucker kann keine Seiten sortieren
	public static final int  DMCOLLATE_TRUE = 1; // Drucker kann Seiten sortieren
	
	// DEVMODE dmDisplayFlags-Konstanten
	public static final int  DM_GRAYSCALE = 1; // Gerät unterstützt keine Farben, Grautöne werden unterstützt
	public static final int  DM_INTERLACED = 2; // Gerät unterstützt Farben
	
	// DEVMODE dmICMMethod-Konstanten
	public static final int  DMICMMETHOD_NONE = 1; // ICM ist abgeschaltet
	public static final int  DMICMMETHOD_SYSTEM = 2; // ICM wird von Windows gesteuert
	public static final int  DMICMMETHOD_DRIVER = 3; // ICM wird vom Treiber gesteuert
	public static final int  DMICMMETHOD_DEVICE = 4; // ICM wird vom Gerät gesteuert
	
	// DEVMODE dmICMIntent-Konstanten
	public static final int  DMICM_SATURATE = 1; // Sättigung wird optimiert
	public static final int  DMICM_CONTRAST = 2; // Kontrast wird optimiert
	public static final int  DMICM_COLORMETRIC = 3; // exakte Farben
	
	// DEVMODE dmMediaType-Konstanten
	public static final int  DMMEDIA_STANDARD = 1; // Standardpapier
	public static final int  DMMEDIA_GLOSSY = 2; // Fotopapier
	public static final int  DMMEDIA_TRANSPARECNY = 3; // Folie
	
	// DEVMODE dmDitherType-Konstanten
	public static final int  DMDITHER_NONE = 1; // kein Dithering
	public static final int  DMDITHER_COARSE = 2; // Dithering mit grobem Brush
	public static final int  DMDITHER_FINE = 3; // Dithering mit feinem Brush
	public static final int  DMDITHER_LINEART = 4; // Linien mit schwarz, weiß und grau
	public static final int  DMDITHER_GRAYSCALE = 5; // Grauskala
	
	// GlobalAlloc wFlags-Konstanten
	public static final int  GHND = 0x40; // Kombination von GMEM_MOVEABLE mit GMEM_ZEROINIT
	public static final int  GMEM_DDESHARE = 0x2000; // optimiert den Speicher mit der DDE
	public static final int  GMEM_DISCARDABLE = 0x100; // Speicher kann überschrieben werden, kann
	// nicht mit GMEM_FIXED kombiniert werden
	public static final int  GMEM_FIXED = 0x0; // fixiert den Speicher und die Funktion gibt einen
	// Pointer zurück
	public static final int  GMEM_MOVEABLE = 0x2; // Der Speicher ist beweglich
	public static final int  GMEM_NOCOMPACT = 0x10; // Speicher wird nicht minimiert oder andere
	// Blöcke für ihn überschrieben
	public static final int  GMEM_NODISCARD = 0x20; // überschreibt keine überschreibbaren Blöcke um
	// den Speicher zu reservieren
	public static final int  GMEM_ZEROINIT = 0x40; // initialisiert den Inhalt des Speicherblocks bei 0
	public static final int  GPTR = 0x42; // kombiniert GMEM_FIXED und GEMEM_ZEROINIT
	
	/*
	DWORD lStructSize;
	HWND hwndOwner;
	HGLOBAL hDevMode;
	HGLOBAL hDevNames;
	HDC hDC;
	DWORD Flags;
	DWORD Flags2;
	DWORD ExclusionFlags;
	DWORD nPageRanges;
	DWORD nMaxPageRanges;
	LPPRINTPAGERANGE lpPageRanges;
	DWORD nMinPage;
	DWORD nMaxPage;
	DWORD nCopies;
	HINSTANCE hInstance;
	LPCTSTR lpPrintTemplateName;
	LPUNKNOWN lpCallback;
	DWORD nPropertyPages;
	HPROPSHEETPAGE *lphPropertyPages;
	DWORD nStartPage;
	DWORD dwResultAction;
	 */
	
	public HWND hwndOwner = new HWND(0);
	public int hDevMode = 0;
	public int hDevNames = 0;
	public DC hDC = new DC(0);
	public DWORD Flags = new DWORD(0);
	public DWORD Flags2 = new DWORD(0);
	public DWORD ExclusionFlags = new DWORD(0);
	public DWORD nPageRanges = new DWORD(0);
	public DWORD nMaxPageRanges = new DWORD(0);
	public LONG lpPageRanges = null;
	public DWORD nMinPage = new DWORD(0);
	public DWORD nMaxPage = new DWORD(0);
	public DWORD nCopies = new DWORD(0);
	public int hInstance = 0;
	public String lpPrintTemplateName;
	public LONG lpCallback = new LONG(0);
	public DWORD nPropertyPages = new DWORD(0);
	public int lphPropertyPages = 0;
	public DWORD nStartPage = new DWORD(0);
	public DWORD dwResultAction = new DWORD(0);
	
	private Pointer lpPrintTemplateNamePointer;
	
	/** Creates a new instance of PRINTDLGEX */
	public PRINTDLGEX() throws NativeException
	{
		super(null);
		createPointer();
		lpPrintTemplateNamePointer = new Pointer(MemoryBlockFactory.createMemoryBlock(256));
	}
	
	private void toPointer() throws NativeException
	{
		
		lpPrintTemplateNamePointer.zeroMemory();
		if(lpPrintTemplateName != null)
		{
			lpPrintTemplateNamePointer.setStringAt(0, lpPrintTemplateName);
		}
		
		offset = 0;
		offset += pointer.setIntAt(offset, getSizeOf());
		offset += pointer.setIntAt(offset, hwndOwner.getValue());
		offset += pointer.setIntAt(offset, hDevMode);
		offset += pointer.setIntAt(offset, hDevNames);
		offset += pointer.setIntAt(offset, hDC.getPointer().getPointer());
		offset += pointer.setIntAt(offset, Flags.getValue());
		offset += pointer.setIntAt(offset, Flags2.getValue());
		offset += pointer.setIntAt(offset, ExclusionFlags.getValue());
		offset += pointer.setIntAt(offset, nPageRanges.getValue());
		offset += pointer.setIntAt(offset, nMaxPageRanges.getValue());
		offset += pointer.setIntAt(offset, lpPageRanges == null ? NullPointer.NULL.getPointer() : lpPageRanges.getPointer().getPointer());
		offset += pointer.setIntAt(offset, nMinPage.getValue());
		offset += pointer.setIntAt(offset, nMaxPage.getValue());
		offset += pointer.setIntAt(offset, nCopies.getValue());
		offset += pointer.setIntAt(offset, hInstance);
		offset += pointer.setIntAt(offset, lpPrintTemplateName == null ? NullPointer.NULL.getPointer() : lpPrintTemplateNamePointer.getPointer());
		offset += pointer.setIntAt(offset, lpCallback.getValue());
		offset += pointer.setIntAt(offset, nPropertyPages.getValue());
		offset += pointer.setIntAt(offset, lphPropertyPages);
		offset += pointer.setIntAt(offset, nStartPage.getValue());
		pointer.setIntAt(offset, dwResultAction.getPointer().getPointer());
		
		offset = 0;
	}
	
	private void fromPointer() throws NativeException
	{
		getNextInt();
		getNextInt();
		hDevMode = getNextInt();
		hDevNames = getNextInt();
		hDC = new DC(getNextInt());
		Flags = new DWORD(getNextInt());
		Flags2 = new DWORD(getNextInt());
		ExclusionFlags = new DWORD(getNextInt());
		nPageRanges = new DWORD(getNextInt());
		nMaxPageRanges = new DWORD(getNextInt());
		getNextInt();
		nMinPage = new DWORD(getNextInt());
		nMaxPage = new DWORD(getNextInt());
		nCopies = new DWORD(getNextInt());
		hInstance = getNextInt();
		getNextInt();
		lpCallback = new LONG(getNextInt());
		nPropertyPages = new DWORD(getNextInt());
		lphPropertyPages = getNextInt();
		nStartPage = new DWORD(getNextInt());
		dwResultAction = new DWORD(getNextInt());
	}
	
	public Pointer createPointer() throws NativeException
	{
		if(pointer == null)
		{
			pointer = new Pointer(new GlobalMemoryBlock(getSizeOf()));
		}
		return pointer;
	}
	
	public int getSizeOf()
	{
		return sizeOf();
	}
	public static int sizeOf()
	{
		return (21*4);
	}
	
	public PRINTDLGEX getValueFromPointer() throws NativeException
	{
		fromPointer();
		return this;
	}
	public PRINTDLGEX getValue()
	{
		try
		{
			toPointer();
		}
		catch (NativeException ex)
		{
			ex.printStackTrace();
		}
		return this;
	}
}
