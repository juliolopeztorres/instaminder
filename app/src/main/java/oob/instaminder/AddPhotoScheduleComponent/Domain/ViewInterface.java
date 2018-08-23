package oob.instaminder.AddPhotoScheduleComponent.Domain;

import oob.instaminder.AddPhotoScheduleComponent.Domain.ExternalStorageLastDialogShownUseCase.ExternalStorageLastDialogShownUseCaseViewInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.GetNumberAdShownUseCase.GetNumberAdShownUseCaseViewRepository;
import oob.instaminder.AddPhotoScheduleComponent.Domain.IncreaseNumberAdShownUseCase.IncreaseNumberAdShownUseCaseViewInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.MarkExternalStorageLastDialogAsShownUseCase.MarkExternalStorageLastDialogAsShownUseCaseViewInterface;
import oob.instaminder.AddPhotoScheduleComponent.Domain.SavePhotoUseCase.SavePhotoUseCaseViewInterface;

public interface ViewInterface extends
        SavePhotoUseCaseViewInterface,
        GetNumberAdShownUseCaseViewRepository,
        IncreaseNumberAdShownUseCaseViewInterface,
        ExternalStorageLastDialogShownUseCaseViewInterface,
        MarkExternalStorageLastDialogAsShownUseCaseViewInterface {
}
