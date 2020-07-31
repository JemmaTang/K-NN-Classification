import java.io.File;
import java.io.IOException;
/**
 * A kNN classification algorithm implementation.
 * 
 */

public class KNN {

	/**
	 * In this method, you should implement the kNN algorithm. You can add 
	 * other methods in this class, or create a new class to facilitate your
	 * work. If you create other classes, DO NOT FORGET to include those java
   * files when preparing your code for hand in.
   *
	 * Also, Please DO NOT MODIFY the parameters or return values of this method,
   * or any other provided code.  Again, create your own methods or classes as
   * you need them.
	 * 
	 * @param trainingData
	 * 		An Item array of training data
	 * @param testData
	 * 		An Item array of test data
	 * @param k
	 * 		The number of neighbors to use for classification
	 * @return
	 * 		The object KNNResult contains classification accuracy, 
	 * 		category assignment, etc.
	 * @throws IOException 
	 */
	public KNNResult classify(Item[] trainingData, Item[] testData, int k) throws IOException {

		/* ... YOUR CODE GOES HERE ... */
		
		// to test the code, remember to change the train and text file numbers accordingly!!
		// p.s. I have put the sample tests outside the folder data.
		String filename1 = "train-0.txt";
		File TrainFileName = new File(filename1);
		trainingData = Utils.readInItem(TrainFileName);
		
		String filename2 = "test-0.txt";
		File TestFileName = new File(filename2);
		testData = Utils.readInItem(TestFileName);
		
		KNNResult KRes = new KNNResult();
		KRes.nearestNeighbors = new String[testData.length][k];
		KRes.categoryAssignment = new String[testData.length];

		int accuracyCount = 0;
		
		// for each test item in testData
		for (int i = 0 ; i < testData.length; i++){
			double [] listD = new double[trainingData.length];
			
			// find kNN in trainingData
			for (int j = 0 ; j < trainingData.length; j++){
				double distance = Math.sqrt(Math.pow(Math.sqrt(Math.pow(testData[i].features[0] - trainingData[j].features[0], 2) + Math.pow(testData[i].features[1] - trainingData[j].features[1], 2)), 2) + Math.pow(testData[i].features[2] - trainingData[j].features[2], 2));					
				listD[j] = distance;
				}	
			
			int fruitCount = 0;
			int nationCount = 0;
			int machineCount = 0;
			
			int minIndex = 0;
			
			for (int l = 0; l < k; l++) {
				for (int x = 0; x < listD.length; x++){
					if (listD[x] < listD[minIndex]){
					minIndex = x;
					}
				}
				int a = minIndex;
				KRes.nearestNeighbors[i][l] = trainingData[a].name;

				if (trainingData[a].category.equals("fruit")){
					fruitCount++;
				}
				if (trainingData[a].category.equals("nation")){
					nationCount++;
				}
				if (trainingData[a].category.equals("machine")){
					machineCount++;
				}
				listD[a] = Double.MAX_VALUE;
			}
			
			if (fruitCount > nationCount){
				if (fruitCount > machineCount){
					KRes.categoryAssignment[i] = "fruit";
				}
			}
			if (nationCount >= machineCount){
				if (nationCount >= fruitCount){
					KRes.categoryAssignment[i] = "nation";
				}
			}
			if (machineCount > nationCount){
				if (machineCount >= fruitCount){
					KRes.categoryAssignment[i] = "machine";
				}
			}
			
			if (KRes.categoryAssignment[i].equals(testData[i].category)){
				accuracyCount++;
			}
		}
      // get predicted category, save in KNNResult.categoryAssignment

      // save kNN in KNNResult.nearestNeighbors

    // calculate accuracy
		KRes.accuracy = (double)accuracyCount / testData.length;
		return KRes;
	}
}
