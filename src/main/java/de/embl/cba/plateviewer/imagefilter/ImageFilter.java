package de.embl.cba.plateviewer.imagefilter;

import bdv.util.BdvOverlay;
import de.embl.cba.plateviewer.loaders.ImageFilterLoader;
import net.imglib2.cache.img.CachedCellImg;
import net.imglib2.cache.img.ReadOnlyCachedCellImgFactory;
import net.imglib2.cache.img.ReadOnlyCachedCellImgOptions;
import net.imglib2.type.NativeType;
import net.imglib2.type.numeric.RealType;
import net.imglib2.type.numeric.integer.UnsignedByteType;

import java.util.ArrayList;

public class ImageFilter < T extends NativeType< T > & RealType< T > >
{

	public static String SIMPLE_SEGMENTATION = "Simple segmentation";
	public static String MEDIAN_DEVIATION = "Local median deviation";
	public static String INFORMATION = "Information";

	private final ImageFilterSettings settings;
	private final String cachedFilterImgName;
	private BdvOverlay bdvOverlay;
	private ImageFilterLoader< T > loader;
	private T type;

	public ImageFilter( ImageFilterSettings settings )
	{
		this.settings = settings;


		this.cachedFilterImgName = settings.inputName + " - " + settings.filterType;

		if ( settings.filterType.equals( ImageFilter.SIMPLE_SEGMENTATION ) )
		{
			type = ( T ) new UnsignedByteType( );
		}
		else
		{
			type = ( T ) settings.inputCachedCellImg.firstElement();
		}

	}

	public BdvOverlay getBdvOverlay()
	{
		return bdvOverlay;
	}


	public String getCachedFilterImgName()
	{
		return cachedFilterImgName;
	}

	public static ArrayList< String > getFilters()
	{
		ArrayList< String > filterTypes = new ArrayList<>(  );
		filterTypes.add( MEDIAN_DEVIATION );
//		filterTypes.add( INFORMATION ); // TODO: too slow...
		filterTypes.add( SIMPLE_SEGMENTATION );

		return filterTypes;
	}


	public CachedCellImg< T, ? > createCachedFilterImg( )
	{
		final CachedCellImg cachedCellImg = settings.inputCachedCellImg;

		int[] cellDimensions = new int[ cachedCellImg.getCellGrid().numDimensions() ];
		cachedCellImg.getCellGrid().cellDimensions( cellDimensions );

		final long[] imgDimensions = cachedCellImg.getCellGrid().getImgDimensions();

		loader = new ImageFilterLoader( settings );

		final CachedCellImg< T, ? > cachedFilterImg = new ReadOnlyCachedCellImgFactory().create(
				imgDimensions,
				type,
				loader,
				ReadOnlyCachedCellImgOptions.options().cellDimensions( cellDimensions ) );

		if ( settings.filterType.equals( ImageFilter.SIMPLE_SEGMENTATION ) )
		{
			bdvOverlay = loader.getBdvOverlay();
		}

		return cachedFilterImg;
	}

}
