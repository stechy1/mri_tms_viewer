package model;

import java.util.Map;
import java.util.TreeMap;
import java.util.Arrays;
import java.lang.reflect.Array;
import controller.Configuration;

import ij.util.DicomTools;
public class Response{
	private final Matrix4x4 matrix = new Matrix4x4();
	private final Map<String,Double> data=new TreeMap<>();
	public Matrix4x4 getMatrix(){
		return matrix;
	}
	public Map<String,Double> getData(){
		return data;
	}
	@Override
	public String toString(){
		return super.toString()+": "+matrix+" = "+data.toString();
	}
	public static Matrix4x4 getMriMatrix(MyDicom mridicom){
		String pixel_spacing[] = DicomTools.getTag(mridicom,"0028,0030").split("\\\\");
		String image_orientation[] = DicomTools.getTag(mridicom,"0020,0037").split("\\\\");
		String image_position[] = DicomTools.getTag(mridicom,"0020,0032").split("\\\\");
		String slice_thickness[] = DicomTools.getTag(mridicom,"0018,0050").split("\\\\");
		double[] img_pos = Arrays.stream(image_position)   .mapToDouble(Double::parseDouble).toArray();
		double[] img_ori = Arrays.stream(image_orientation).mapToDouble(Double::parseDouble).toArray();
		double[] pxl_spc = Arrays.stream(pixel_spacing)    .mapToDouble(Double::parseDouble).toArray();
		double[] slc_thk = Arrays.stream(slice_thickness)  .mapToDouble(Double::parseDouble).toArray();
		Matrix4x4 mri = new Matrix4x4();
		mri.getData()[0]=pxl_spc[1]*img_ori[0];
		mri.getData()[1]=pxl_spc[0]*img_ori[3];
		mri.getData()[2]=(img_ori[5]*img_ori[1]-img_ori[4]*img_ori[2])*slc_thk[0];
		mri.getData()[3]=img_pos[0];

		mri.getData()[4]=pxl_spc[1]*img_ori[1];
		mri.getData()[5]=pxl_spc[0]*img_ori[4];
		mri.getData()[6]=(img_ori[3]*img_ori[2]-img_ori[5]*img_ori[0])*slc_thk[0];
		mri.getData()[7]=img_pos[1];

		mri.getData()[8]=pxl_spc[1]*img_ori[2];
		mri.getData()[9]=pxl_spc[0]*img_ori[5];
		mri.getData()[10]=(img_ori[4]*img_ori[0]-img_ori[3]*img_ori[1])*slc_thk[0];
		mri.getData()[11]=img_pos[2];

		mri.getData()[15]=1;
		return mri;
	}
	public void calculateCoords(MyDicom mridicom,double[] img_coords){
		Matrix4x4 mri = getMriMatrix(mridicom);
		Matrix4x4 inv = mri.inverse();
		double[] vec = {matrix.getData()[3],matrix.getData()[7],matrix.getData()[11],matrix.getData()[15]};
		inv.multiply_by_vector(vec,img_coords);
		/*Matrix4x4 conv = inv.multiply(matrix);
		img_coords[0] = conv.getData()[3]/conv.getData()[15];
		img_coords[1] = conv.getData()[7]/conv.getData()[15];
		img_coords[2] = conv.getData()[11]/conv.getData()[15];*/
		//System.out.println(mri+"->\n"+inv+"*\n"+matrix+"=\n"+conv);
		//System.out.println(img_coords[0]+","+img_coords[1]+","+img_coords[2]);
		//System.out.println(matrix.getData()[3]+","+matrix.getData()[7]+","+matrix.getData()[11]);
		//TODO not include in release:
		data.put("X",img_coords[0]);
		data.put("Y",img_coords[1]);
		data.put("Z",img_coords[2]);
	}
}
