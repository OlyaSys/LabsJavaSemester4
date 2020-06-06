package sample;

import sample.controller.*;

public class StageConfig
{
    private static WorkController workController = null;
    private static CarController carController = null;
    private static MasterController masterController = null;
    private static ServiceController serviceController = null;
    private static Controller controller = null;
    private static AddController addController = null;

    public static void setWorkController(WorkController workController_val)
    {
        StageConfig.workController = workController_val;
    }

    public static void setCarController(CarController carController_val)
    {
        StageConfig.carController = carController_val;
    }

    public static void setMasterController(MasterController masterController_val)
    {
        StageConfig.masterController = masterController_val;
    }

    public static void setServiceController(ServiceController serviceController_val)
    {
        StageConfig.serviceController = serviceController_val;
    }

    public static void setAddController(AddController addController_val)
    {
        StageConfig.addController = addController_val;
    }

    public static WorkController getWorkController()
    {
        return StageConfig.workController;
    }

    public static CarController getCarController()
    {
        return StageConfig.carController;
    }

    public static MasterController getMasterController()
    {
        return StageConfig.masterController;
    }

    public static ServiceController getServiceController()
    {
        return StageConfig.serviceController;
    }

    public static AddController getAddController()
    {
        return StageConfig.addController;
    }

    public static void setController(Controller ctl_val)
    {
        StageConfig.controller = ctl_val;
    }

    public static Controller getController()
    {
        return StageConfig.controller;
    }
}