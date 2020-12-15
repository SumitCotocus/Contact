package com.example.contact;

public class NetworkManager
{
    enum apiType {
        getList, newEmail, updateEmail, deleteEmail;

    }

static void hitApi(apiType type, String emailText, String dataParameter)
{
        String baseURL = "https://devfrontend.gscmaven.com/wmsweb/webapi/email/";
        switch (type) {
            case getList:
        }
}
}
