import json
import boto3
import datetime
import os
from urllib.parse import unquote_plus

# boto3 S3 initialization
s3_client = boto3.client("s3")


def lambda_handler(event, context):
   destination_bucket_name = 'problem2-destination-bucket'

   # event contains all information about uploaded object
   print("Event :", event)

   # Bucket Name where file was uploaded
   source_bucket_name = event['Records'][0]['s3']['bucket']['name']

   # Filename of object (with path)
   file_key_name = event['Records'][0]['s3']['object']['key']
   file_key_name = unquote_plus(file_key_name)
   print("File name is:")
   print(file_key_name)
   
   dt = str(datetime.datetime.now())
   filename, file_extension = os.path.splitext(file_key_name)
   newname = filename+dt+file_extension

   # Copy Source Object
   copy_source_object = {'Bucket': source_bucket_name, 'Key': file_key_name}

   # S3 copy object operation
   s3_client.copy_object(CopySource=copy_source_object, Bucket=destination_bucket_name, Key=newname)

   return {
       'statusCode': 200,
       'body': json.dumps('Hello from S3 events Lambda!')
   }
