# InteractiveLearner
To run this you need java 8
The files should be stored in TrainingFiles with as subdirectory the category (e.g. mail, blogs)
To run the interactive learner, run the Controller class.
To change the trainingfiles from mails to blogs, change that in the controller class like such:
        bayes = new NaiveBayes("../InteractiveLearner/TrainingFiles/blogs");
The GUI offers a very simple representation of what is happening in the IL. To test it you have to manually
copy the testfiles to the textarea and click classify button to see how it is classified.
