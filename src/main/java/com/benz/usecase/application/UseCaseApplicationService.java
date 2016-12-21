package com.benz.usecase.application;

import com.benz.framework.AssertionConcern;
import com.benz.usecase.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by paulliu on 2016/12/12.
 */
@Service
public class UseCaseApplicationService {

    @Autowired
    private UseCaseRepository useCaseRepository;

    public String createUseCase(UseCaseCreateCommand command) {
        UseCase useCase =
                new UseCase(
                        new CaseSubject(command.getSubject()),
                        command.getValue(),
                        command.getScenario(),
                        command.getResult(),
                        command.getDataSource(),
                        command.getDataTypeDescription(),
                        command.getAnalysisModel(),
                        command.getContributionBU(),
                        command.getStage(),
                        new TimeFrame(command.getFromDate(), command.getToDate()),
                        command.getContact()
                );

        useCase.create();
        return useCase.getId();
    }

    public void updateUseCase(UseCaseUpdateCommand command) {
        UseCase useCase = existing(command.getId());
        useCase.update(
                new CaseSubject(command.getSubject()),
                command.getValue(),
                command.getScenario(),
                command.getResult(),
                command.getDataSource(),
                command.getDataTypeDescription(),
                command.getAnalysisModel(),
                command.getContributionBU(),
                command.getStage(),
                new TimeFrame(command.getFromDate(), command.getToDate()),
                command.getContact());
    }

    public void deleteUseCase(String id) {
        UseCase useCase = existing(id);
        useCase.delete();
    }

    private UseCase existing(String id) {
        UseCase useCase = useCaseRepository.getOne(id);
        System.out.println(useCase);
        AssertionConcern.assertArgumentNotNull(useCase, "use case does not exist");
        return useCase;
    }


}
