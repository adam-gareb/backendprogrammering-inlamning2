package se.yrgo.services.calls;

import se.yrgo.domain.Action;
import se.yrgo.domain.Call;
import se.yrgo.domain.Customer;
import se.yrgo.services.customers.CustomerManagementService;
import se.yrgo.services.customers.CustomerNotFoundException;
import se.yrgo.services.diary.DiaryManagementService;

import org.springframework.transaction.annotation.Transactional;
import java.util.Collection;

@Transactional
public class CallHandlingServiceImpl implements CallHandlingService {

    private CustomerManagementService customerService;
    private DiaryManagementService diaryService;

    public CallHandlingServiceImpl(CustomerManagementService customerService, DiaryManagementService diaryService) {
        this.customerService = customerService;
        this.diaryService = diaryService;
    }

    @Override
    public void recordCall(String customerId, Call newCall, Collection<Action> actions) throws CustomerNotFoundException {
        customerService.recordCall(customerId, newCall);

        for (Action action : actions){
            diaryService.recordAction(action);
        }
    }
}
