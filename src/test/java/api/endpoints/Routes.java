package api.endpoints;
/*
Incidence URI : http://ec2-43-205-70-111.ap-south-1.compute.amazonaws.com:8081

Login(POST) : http://ec2-43-205-70-111.ap-south-1.compute.amazonaws.com:8081/auth/signin
Inventory listing(GET) : http://ec2-43-205-70-111.ap-south-1.compute.amazonaws.com:8081/asset2/v1/assets/inventory
Incidence list(GET) : http://ec2-43-205-70-111.ap-south-1.compute.amazonaws.com:8081/asset2/v1/assets/incidence
Incidence history(GET) : http://ec2-43-205-70-111.ap-south-1.compute.amazonaws.com:8081/asset2/v1/assets/incidence-history/{assetId}
Incidence details(GET) : http://ec2-43-205-70-111.ap-south-1.compute.amazonaws.com:8081/asset2/v1/assets/incidence-details?assetId={assetId}
Report incidence(POST) : http://ec2-43-205-70-111.ap-south-1.compute.amazonaws.com:8081/asset2/v1/email/report-incident
Resolve incidence(POST) : http://ec2-43-205-70-111.ap-south-1.compute.amazonaws.com:8081/asset2/v1/assets/resolve-incidence
Reporter resolver details(GET) : http://ec2-43-205-70-111.ap-south-1.compute.amazonaws.com:8081/asset2/v1/transfer/resolver-reporter-details/{incidenceId}

 */
public class Routes {

    public static String base_url="http://ec2-43-205-70-111.ap-south-1.compute.amazonaws.com:8081";

    //Incidence module
    public static String login_url=base_url+"/auth/signin";
    public static String inventory_listing_url=base_url+"/asset2/v1/assets/inventory";
    public static String incidence_list_url=base_url+"/asset/v1/assets/incidence";
    public static String incidence_history_url=base_url+"/asset2/v1/assets/incidence-history/{assetId}";
    public static String incidence_details_url=base_url+"/asset2/v1/assets/incidence-details?assetId={assetId}";
    public static String report_incidence_url=base_url+"/asset2/v1/email/report-incident";
    public static String resolve_incidence_url=base_url+"/asset2/v1/assets/resolve-incidence";
    public static String reporter_resolver_url=base_url+"/asset2/v1/transfer/resolver-reporter-details/{incidenceId}";
}
