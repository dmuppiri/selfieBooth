package com.orbitdental.selfiebooth;

import android.content.Context;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.s3.AmazonS3Client;

/**
 * Created by dpk on 1/23/18.
 */

public class Util {

    // We only need one instance of the clients and credentials provider
    public static TransferUtility transferUtility;
    public static DynamoDBMapper dynamoDBMapper;
    /**
     * Gets an instance of the TransferUtility which is constructed using the
     * given Context
     *
     * @param context
     * @return a TransferUtility instance
     */
    public static void setTransferUtility(Context context) {
        AmazonS3Client amazonS3Client = new AmazonS3Client(AWSMobileClient.getInstance().getCredentialsProvider());
        amazonS3Client.setRegion(Region.getRegion(Regions.US_EAST_1));
            transferUtility = TransferUtility.builder()
                            .context(context)
                            .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                            .s3Client(amazonS3Client)
                            .build();
        }
    public static DynamoDBMapper getDynamoDBMapper(){
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(AWSMobileClient.getInstance().getCredentialsProvider());
        dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                .build();
        return dynamoDBMapper;
        }
    }
