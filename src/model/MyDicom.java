package model;

import enums.DicomTags;
import ij.plugin.DICOM;
import ij.util.DicomTools;

public class MyDicom extends DICOM implements Comparable<MyDicom>{

	@Override
	public int compareTo(MyDicom dicom) {
		int x = Integer.parseInt(DicomTools.getTag(this, DicomTags.IMAGE_NUMBER.getIdentifier()).trim());
		int y =  Integer.parseInt(DicomTools.getTag(dicom, DicomTags.IMAGE_NUMBER.getIdentifier()).trim());
		
		return Integer.compare(x, y);
	}

}
