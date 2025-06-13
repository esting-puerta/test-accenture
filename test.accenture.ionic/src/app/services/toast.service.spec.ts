import { TestBed } from '@angular/core/testing';
import { ToastService } from './toast.service';
import { ToastController } from '@ionic/angular';

describe('ToastService', () => {
    
  let service: ToastService;
  let toastControllerSpy: jest.Mocked<ToastController>;

  beforeEach(() => {
    const spy = {
      create: jest.fn().mockReturnValue(Promise.resolve({
        present: () => Promise.resolve()
      }))
    };

    TestBed.configureTestingModule({
      providers: [
        ToastService,
        { provide: ToastController, useValue: spy }
      ]
    });
    service = TestBed.inject(ToastService);
    toastControllerSpy = TestBed.inject(ToastController) as jest.Mocked<ToastController>;
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should show success toast with default duration', async () => {
    await service.showSuccess('Success message');
    expect(toastControllerSpy.create).toHaveBeenCalledWith({
      message: 'Success message',
      duration: 2000,
      position: 'top',
      color: 'success',
      buttons: [
        {
          icon: 'close',
          role: 'cancel'
        }
      ]
    });
  });

  it('should show success toast with custom duration', async () => {
    await service.showSuccess('Success message', 5000);
    expect(toastControllerSpy.create).toHaveBeenCalledWith({
      message: 'Success message',
      duration: 5000,
      position: 'top',
      color: 'success',
      buttons: [
        {
          icon: 'close',
          role: 'cancel'
        }
      ]
    });
  });

  it('should show error toast with default duration', async () => {
    await service.showError('Error message');
    expect(toastControllerSpy.create).toHaveBeenCalledWith({
      message: 'Error message',
      duration: 3000,
      position: 'top',
      color: 'danger',
      buttons: [
        {
          icon: 'close',
          role: 'cancel'
        }
      ]
    });
  });

  it('should show error toast with custom duration', async () => {
    await service.showError('Error message', 5000);
    expect(toastControllerSpy.create).toHaveBeenCalledWith({
      message: 'Error message',
      duration: 5000,
      position: 'top',
      color: 'danger',
      buttons: [
        {
          icon: 'close',
          role: 'cancel'
        }
      ]
    });
  });

  it('should show warning toast with default duration', async () => {
    await service.showWarning('Warning message');
    expect(toastControllerSpy.create).toHaveBeenCalledWith({
      message: 'Warning message',
      duration: 2500,
      position: 'top',
      color: 'warning',
      buttons: [
        {
          icon: 'close',
          role: 'cancel'
        }
      ]
    });
  });

  it('should show warning toast with custom duration', async () => {
    await service.showWarning('Warning message', 5000);
    expect(toastControllerSpy.create).toHaveBeenCalledWith({
      message: 'Warning message',
      duration: 5000,
      position: 'top',
      color: 'warning',
      buttons: [
        {
          icon: 'close',
          role: 'cancel'
        }
      ]
    });
  });

  it('should show info toast with default duration', async () => {
    await service.showInfo('Info message');
    expect(toastControllerSpy.create).toHaveBeenCalledWith({
      message: 'Info message',
      duration: 2000,
      position: 'top',
      color: 'primary',
      buttons: [
        {
          icon: 'close',
          role: 'cancel'
        }
      ]
    });
  });

  it('should show info toast with custom duration', async () => {
    await service.showInfo('Info message', 5000);
    expect(toastControllerSpy.create).toHaveBeenCalledWith({
      message: 'Info message',
      duration: 5000,
      position: 'top',
      color: 'primary',
      buttons: [
        {
          icon: 'close',
          role: 'cancel'
        }
      ]
    });
  });
}); 