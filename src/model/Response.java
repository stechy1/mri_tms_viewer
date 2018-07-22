package model;

import java.util.Map;
import java.util.HashMap;
import java.util.Arrays;
public class Response{
	private final double[] matrix=new double[16];
	private final Map<String,Double> data=new HashMap<>();
	public double[] getMatrix(){
		return matrix;
	}
	public Map<String,Double> getData(){
		return data;
	}
	@Override
	public String toString(){
		return super.toString()+": ["+Arrays.toString(matrix)+"] = "+data.toString();
	}
	public void calculateCoords(int[] img_coords){
		inverseMatrix();
		//TODO
		img_coords[0] = (int)matrix[3];
		img_coords[1] = (int)matrix[7];
		img_coords[2] = (int)matrix[11];
	}
	private void inverseMatrix(){
		double[] x=new double[35];
		x[0]=matrix[10]*matrix[15]-matrix[11]*matrix[14];
		x[1]=matrix[9]*matrix[15]-matrix[11]*matrix[13];
		x[2]=matrix[9]*matrix[14]-matrix[10]*matrix[13];
		x[3]=matrix[8]*matrix[15]-matrix[11]*matrix[12];
		x[4]=matrix[8]*matrix[14]-matrix[10]*matrix[12];
		x[5]=matrix[8]*matrix[13]-matrix[9]*matrix[12];
		x[6]=matrix[6]*matrix[15]-matrix[7]*matrix[14];
		x[7]=matrix[5]*matrix[15]-matrix[7]*matrix[13];
		x[8]=matrix[5]*matrix[14]-matrix[6]*matrix[13];
		x[9]=matrix[6]*matrix[11]-matrix[7]*matrix[10];
		x[10]=matrix[5]*matrix[11]-matrix[7]*matrix[9];
		x[11]=matrix[5]*matrix[10]-matrix[6]*matrix[9];
		x[12]=matrix[4]*matrix[15]-matrix[7]*matrix[12];
		x[13]=matrix[4]*matrix[14]-matrix[6]*matrix[12];
		x[14]=matrix[4]*matrix[11]-matrix[7]*matrix[8];
		x[15]=matrix[4]*matrix[10]-matrix[6]*matrix[8];
		x[16]=matrix[4]*matrix[13]-matrix[5]*matrix[12];
		x[16]=matrix[4]*matrix[9]-matrix[5]*matrix[8];
		x[18]=matrix[0]*(matrix[5]*x[0]-matrix[6]*x[1]+matrix[7]*x[2])-
			matrix[1]*(matrix[4]*x[0]-matrix[6]*x[3]+matrix[7]*x[4])+
			matrix[2]*(matrix[4]*x[1]-matrix[5]*x[3]+matrix[7]*x[5])-
			matrix[3]*(matrix[4]*x[2]-matrix[5]*x[4]+matrix[6]*x[5]);
		x[18]=1/x[18];
		x[19]=x[18]*(matrix[5]*x[0]-matrix[6]*x[1]+matrix[7]*x[2]);
		x[20]=x[18]*-(matrix[1]*x[0]-matrix[2]*x[1]+matrix[3]*x[2]);
		x[21]=x[18]*(matrix[1]*x[6]-matrix[2]*x[7]+matrix[3]*x[8]);
		x[22]=x[18]*-(matrix[1]*x[9]-matrix[2]*x[10]+matrix[3]*x[11]);
		x[23]=x[18]*-(matrix[4]*x[0]-matrix[6]*x[3]+matrix[7]*x[4]);
		x[24]=x[18]*(matrix[0]*x[0]-matrix[2]*x[3]+matrix[3]*x[4]);
		x[25]=x[18]*-(matrix[0]*x[6]-matrix[2]*x[12]+matrix[3]*x[13]);
		x[26]=x[18]*(matrix[0]*x[9]-matrix[2]*x[14]+matrix[3]*x[15]);
		x[27]=x[18]*(matrix[4]*x[1]-matrix[5]*x[3]+matrix[7]*x[5]);
		x[28]=x[18]*-(matrix[0]*x[1]-matrix[1]*x[3]+matrix[3]*x[5]);
		x[29]=x[18]*(matrix[0]*x[7]-matrix[1]*x[12]+matrix[3]*x[16]);
		x[30]=x[18]*-(matrix[0]*x[10]-matrix[1]*x[14]+matrix[3]*x[16]);
		x[31]=x[18]*-(matrix[4]*x[2]-matrix[5]*x[4]+matrix[6]*x[5]);
		x[32]=x[18]*(matrix[0]*x[2]-matrix[1]*x[4]+matrix[2]*x[5]);
		x[33]=x[18]*-(matrix[0]*x[8]-matrix[1]*x[13]+matrix[2]*x[16]);
		x[34]=x[18]*(matrix[0]*x[11]-matrix[1]*x[15]+matrix[2]*x[16]);
		matrix[0]=x[19];
		matrix[1]=x[20];
		matrix[2]=x[21];
		matrix[3]=x[22];
		matrix[4]=x[23];
		matrix[5]=x[24];
		matrix[6]=x[25];
		matrix[7]=x[26];
		matrix[8]=x[27];
		matrix[9]=x[28];
		matrix[10]=x[29];
		matrix[11]=x[30];
		matrix[12]=x[31];
		matrix[13]=x[32];
		matrix[14]=x[33];
		matrix[15]=x[34];
	}
}
