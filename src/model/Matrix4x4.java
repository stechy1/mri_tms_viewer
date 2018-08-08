package model;
import java.io.Serializable;

public class Matrix4x4 implements Serializable{

	private final double[] matrix=new double[16];

	public double[] getData(){
		return matrix;
	}

	public Matrix4x4(){
		matrix[0]=1;
		matrix[5]=1;
		matrix[10]=1;
		matrix[15]=1;
	}

	public Matrix4x4(Matrix4x4 cpy){
		System.arraycopy(cpy.matrix,0,matrix,0,cpy.matrix.length);
	}

	public void multiply_by_vector(double[] data,double[] res){
		res[0] = matrix[0]*data[0]+matrix[1]*data[1]+matrix[2]*data[2]+matrix[3]*data[3];
		res[1] = matrix[4]*data[0]+matrix[5]*data[1]+matrix[6]*data[2]+matrix[7]*data[3];
		res[2] = matrix[8]*data[0]+matrix[9]*data[1]+matrix[10]*data[2]+matrix[11]*data[3];
		res[3] = matrix[12]*data[0]+matrix[13]*data[1]+matrix[14]*data[2]+matrix[15]*data[3];
	}

	public Matrix4x4 multiply(Matrix4x4 mat){
		Matrix4x4 res = new Matrix4x4();
		res.matrix[0]  = matrix[0] *mat.matrix[0]+matrix[1] *mat.matrix[4]+matrix[2] *mat.matrix[8] +matrix[3] *mat.matrix[12];
		res.matrix[1]  = matrix[0] *mat.matrix[1]+matrix[1] *mat.matrix[5]+matrix[2] *mat.matrix[9] +matrix[3] *mat.matrix[13];
		res.matrix[2]  = matrix[0] *mat.matrix[2]+matrix[1] *mat.matrix[6]+matrix[2] *mat.matrix[10]+matrix[3] *mat.matrix[14];
		res.matrix[3]  = matrix[0] *mat.matrix[3]+matrix[1] *mat.matrix[7]+matrix[2] *mat.matrix[11]+matrix[3] *mat.matrix[15];
		res.matrix[4]  = matrix[4] *mat.matrix[0]+matrix[5] *mat.matrix[4]+matrix[6] *mat.matrix[8] +matrix[7] *mat.matrix[12];
		res.matrix[5]  = matrix[4] *mat.matrix[1]+matrix[5] *mat.matrix[5]+matrix[6] *mat.matrix[9] +matrix[7] *mat.matrix[13];
		res.matrix[6]  = matrix[4] *mat.matrix[2]+matrix[5] *mat.matrix[6]+matrix[6] *mat.matrix[10]+matrix[7] *mat.matrix[14];
		res.matrix[7]  = matrix[4] *mat.matrix[3]+matrix[5] *mat.matrix[7]+matrix[6] *mat.matrix[11]+matrix[7] *mat.matrix[15];
		res.matrix[8]  = matrix[8] *mat.matrix[0]+matrix[9] *mat.matrix[4]+matrix[10]*mat.matrix[8] +matrix[11]*mat.matrix[12];
		res.matrix[9]  = matrix[8] *mat.matrix[1]+matrix[9] *mat.matrix[5]+matrix[10]*mat.matrix[9] +matrix[11]*mat.matrix[13];
		res.matrix[10] = matrix[8] *mat.matrix[2]+matrix[9] *mat.matrix[6]+matrix[10]*mat.matrix[10]+matrix[11]*mat.matrix[14];
		res.matrix[11] = matrix[8] *mat.matrix[3]+matrix[9] *mat.matrix[7]+matrix[10]*mat.matrix[11]+matrix[11]*mat.matrix[15];
		res.matrix[12] = matrix[12]*mat.matrix[0]+matrix[13]*mat.matrix[4]+matrix[14]*mat.matrix[8] +matrix[15]*mat.matrix[12];
		res.matrix[13] = matrix[12]*mat.matrix[1]+matrix[13]*mat.matrix[5]+matrix[14]*mat.matrix[9] +matrix[15]*mat.matrix[13];
		res.matrix[14] = matrix[12]*mat.matrix[2]+matrix[13]*mat.matrix[6]+matrix[14]*mat.matrix[10]+matrix[15]*mat.matrix[14];
		res.matrix[15] = matrix[12]*mat.matrix[3]+matrix[13]*mat.matrix[7]+matrix[14]*mat.matrix[11]+matrix[15]*mat.matrix[15];
		return res;
	}

	public Matrix4x4 inverse(){
		double[] x=new double[19];
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
		x[17]=matrix[4]*matrix[9]-matrix[5]*matrix[8];
		x[18]=matrix[0]*(matrix[5]*x[0]-matrix[6]*x[1]+matrix[7]*x[2])-
			matrix[1]*(matrix[4]*x[0]-matrix[6]*x[3]+matrix[7]*x[4])+
			matrix[2]*(matrix[4]*x[1]-matrix[5]*x[3]+matrix[7]*x[5])-
			matrix[3]*(matrix[4]*x[2]-matrix[5]*x[4]+matrix[6]*x[5]);
		x[18]=1/x[18];
		Matrix4x4 mat = new Matrix4x4();
		mat.matrix[0]=x[18]*(matrix[5]*x[0]-matrix[6]*x[1]+matrix[7]*x[2]);
		mat.matrix[1]=x[18]*-(matrix[1]*x[0]-matrix[2]*x[1]+matrix[3]*x[2]);
		mat.matrix[2]=x[18]*(matrix[1]*x[6]-matrix[2]*x[7]+matrix[3]*x[8]);
		mat.matrix[3]=x[18]*-(matrix[1]*x[9]-matrix[2]*x[10]+matrix[3]*x[11]);
		mat.matrix[4]=x[18]*-(matrix[4]*x[0]-matrix[6]*x[3]+matrix[7]*x[4]);
		mat.matrix[5]=x[18]*(matrix[0]*x[0]-matrix[2]*x[3]+matrix[3]*x[4]);
		mat.matrix[6]=x[18]*-(matrix[0]*x[6]-matrix[2]*x[12]+matrix[3]*x[13]);
		mat.matrix[7]=x[18]*(matrix[0]*x[9]-matrix[2]*x[14]+matrix[3]*x[15]);
		mat.matrix[8]=x[18]*(matrix[4]*x[1]-matrix[5]*x[3]+matrix[7]*x[5]);
		mat.matrix[9]=x[18]*-(matrix[0]*x[1]-matrix[1]*x[3]+matrix[3]*x[5]);
		mat.matrix[10]=x[18]*(matrix[0]*x[7]-matrix[1]*x[12]+matrix[3]*x[16]);
		mat.matrix[11]=x[18]*-(matrix[0]*x[10]-matrix[1]*x[14]+matrix[3]*x[17]);
		mat.matrix[12]=x[18]*-(matrix[4]*x[2]-matrix[5]*x[4]+matrix[6]*x[5]);
		mat.matrix[13]=x[18]*(matrix[0]*x[2]-matrix[1]*x[4]+matrix[2]*x[5]);
		mat.matrix[14]=x[18]*-(matrix[0]*x[8]-matrix[1]*x[13]+matrix[2]*x[16]);
		mat.matrix[15]=x[18]*(matrix[0]*x[11]-matrix[1]*x[15]+matrix[2]*x[17]);
		return mat;
	}

    @Override
    public String toString(){
        return new StringBuilder("/ ").append(String.format("%+.5e",matrix[0])).append(" ").append(String.format("%+.5e",matrix[1])).append(" ")
            .append(String.format("%+.5e",matrix[2])).append(" ").append(String.format("%+.5e",matrix[3])).append(" \\\n| ")
            .append(String.format("%+.5e",matrix[4])).append(" ").append(String.format("%+.5e",matrix[5])).append(" ")
            .append(String.format("%+.5e",matrix[6])).append(" ").append(String.format("%+.5e",matrix[7])).append(" |\n| ")
            .append(String.format("%+.5e",matrix[8])).append(" ").append(String.format("%+.5e",matrix[9])).append(" ")
            .append(String.format("%+.5e",matrix[10])).append(" ").append(String.format("%+.5e",matrix[11])).append(" |\n\\ ")
            .append(String.format("%+.5e",matrix[12])).append(" ").append(String.format("%+.5e",matrix[13])).append(" ")
            .append(String.format("%+.5e",matrix[14])).append(" ").append(String.format("%+.5e",matrix[15])).append(" /").toString();
    }
}
