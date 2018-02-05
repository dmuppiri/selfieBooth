package com.orbitdental.selfiebooth;

/**
 * Created by dpk on 1/28/18.
 */

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Map;
import java.util.Set;

@DynamoDBTable(tableName = "selfiebooth-mobilehub-1034723891-user-uploads")

public class UserUploadsDO {
    private String _fileid;
    private String _email;
    private Double _mobile;

    @DynamoDBHashKey(attributeName = "fileid")
    @DynamoDBAttribute(attributeName = "fileid")
    public String getFileid() {
        return _fileid;
    }

    public void setFileid(final String _fileid) {
        this._fileid = _fileid;
    }
    @DynamoDBAttribute(attributeName = "email")
    public String getEmail() {
        return _email;
    }

    public void setEmail(final String _email) {
        this._email = _email;
    }
    @DynamoDBAttribute(attributeName = "mobile")
    public Double getMobile() {
        return _mobile;
    }

    public void setMobile(final Double _mobile) {
        this._mobile = _mobile;
    }

}