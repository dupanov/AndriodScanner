package message;

import java.util.Scanner;

/**
 * Created by admin on 15.01.2016.
 */
public class MailList {
        public MailList() {
            double dim1, dim2, dim3;
            double temp1, temp2, temp3;

            Scanner scan = new Scanner(System.in);
            System.out.println("Enter First Dimension");
            temp1 = scan.nextDouble();
            System.out.println("Enter Second Dimension");
            temp2 = scan.nextDouble();
            System.out.println("Enter Third Dimension");
            temp3 = scan.nextDouble();

            dim1 = Math.max(temp3, Math.max(temp1, temp2));

            if (dim1 == temp1) {
                dim2 = temp2;
                dim3 = temp3;
            }else if (dim1 == temp2) {
                dim2 = temp1;
                dim3 = temp3;
            }else if (dim1 == temp3) {
                dim2 = temp1;
                dim3 = temp2;
            }

        }

        public void setOrder() {

        }

        public void doMath()    {

        }

        public static void main (String[] args) {
            MailList list = new MailList();
            list.doMath();
        }
    }

