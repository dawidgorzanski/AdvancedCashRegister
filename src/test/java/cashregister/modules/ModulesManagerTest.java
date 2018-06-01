package cashregister.modules;

import cashregister.modules.interfaces.IBarcodeChecker;
import cashregister.modules.interfaces.IPaymentModule;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static cashregister.modules.ModulesManager.getObjectByType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(MockitoJUnitRunner.class)
public class ModulesManagerTest {

    ModulesManager modulesManager;

    @Before
    public void setUp(){
        modulesManager = new ModulesManager();
        modulesManager.initialize();
    }

    @Test
    public void getObjectByType_ArgumentIsTypeOfIBarcodeChecker_ReturnBarcodeCheckerType()
    {
        assertTrue((getObjectByType(IBarcodeChecker.class)) instanceof IBarcodeChecker);
    }

    @Test
    public void getObjectsByType_ArgumentIsTypeOfIBarcodeChecker_ReturnListOfBarcodeCheckerType()
    {
        List<IBarcodeChecker> list = new ArrayList<IBarcodeChecker>();
        assertEquals(list.getClass(), modulesManager.getObjectsByType(IBarcodeChecker.class).getClass());
    }

}