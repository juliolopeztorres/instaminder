package oob.instaminder.AddPhotoScheduleComponent.Domain;

import oob.instaminder.AddPhotoScheduleComponent.Domain.GetNumberAdShownUseCase.GetNumberAdShownUseCaseViewRepository;
import oob.instaminder.AddPhotoScheduleComponent.Domain.IncreaseNumberAdShownUseCase.IncreaseNumberAdShownUseCaseViewInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCaseViewInterface;

public interface ViewInterface extends
        SavePhotoUseCaseViewInterface,
        GetNumberAdShownUseCaseViewRepository,
        IncreaseNumberAdShownUseCaseViewInterface {
}
