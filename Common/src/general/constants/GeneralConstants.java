package general.constants;

public class GeneralConstants {
    public static final String BASE_URL = "http://localhost:8080/PredictionsWebApplication";

    public static final String UPLOADED_FILE_NAME = "file";
    public static final String THREAD_POOL_SIZE_PARAMETER_NAME = "size";
    public static final String WORLD_NAME_PARAMETER_NAME = "name";
    public static final String REQUEST_ID_PARAMETER_NAME = "id";
    public static final String NEW_STATUS_PARAMETER_NAME = "status";
    public static final String USER_NAME_PARAMETER_NAME = "username";

    public static final String GET_LOADED_WORLDS_RESOURCE = "/info/loaded_worlds";
    public static final String GET_WORLD_RESOURCE = "/info/world";
    public static final String GET_ENVIRONMENT_VARIABLES_TO_SET_RESOURCE = "/info/environment_variables_to_set";
    public static final String GET_SIMULATION_QUEUE_DETAILS_RESOURCE = "/info/simulation_queue";
    public static final String GET_ALL_USER_REQUESTS_RESOURCE = "/info/user_requests";

    public static final String NEW_WORLD_UPLOAD_RESOURCE = "/new_world_upload";
    public static final String SET_THREAD_POOL_SIZE_RESOURCE = "/set_thread_pool_size";
    public static final String ALLOCATION_REQUEST_RESOURCE = "/allocation_request";
    public static final String CHANGE_REQUEST_STATUS_RESOURCE = "/change_request_status";
    public static final String LOGIN_RESOURCE = "/login_page";
    public static final String LOGOUT_RESOURCE = "/logout_page";

    public static final int MAX_THREAD_POOL_SIZE = 35;
}
