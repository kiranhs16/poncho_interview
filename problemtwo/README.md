I decided to use S3 + Lambda to solve the problem

1. Validate the file extension to be pdf only. 
Ans: This can be done by adding filter to the S3 event itself. To the Lambda funtion add S3 event as trigger for PUT with file suffix being .pdf

Create a new lambda funtion from scratch

Add S3 as the trigger and point the source bucket.

Copy the lambda_function.py into a newly created Lambda funtion with python as runner.

Create a destination bucket named as "problem2-destination-bucket"

Create an IAM role the Lambda function can use and assign the policy to access Source bucket and add files to the destination bucket.

Upload a PDF file to the source bucket. 
