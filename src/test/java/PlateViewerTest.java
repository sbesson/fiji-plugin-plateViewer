import de.embl.cba.plateviewer.PlateViewer;

public class PlateViewerTest
{
	public static void main( String[] args )
	{
//		new PlateViewer(
//				PlateViewerTest.class.getResource( "ALMF-EMBL-P2-S4-C2-T1" ).getFile(),
//				".*.tif",
//				1);

		new PlateViewer(
				"/Volumes/pepperkok/mkhan/FDAscreen/testplate04/20181203_175618_645/",
				".*.tif",
				1);
	}
}
