import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ResponseService {
  private responseSubject = new BehaviorSubject<{ message: string, isSuccess: boolean, show: boolean }>({
    message: '',
    isSuccess: false,
    show: false
  });

  public response$ = this.responseSubject.asObservable();

  constructor() { }

  showResponse(message: string, isSuccess: boolean): void {
    this.responseSubject.next({
      message,
      isSuccess,
      show: true
    });

    // Ocultar después de 5 segundos
    setTimeout(() => {
      this.hideResponse();
    }, 5000);
  }

  hideResponse(): void {
    const current = this.responseSubject.value;
    this.responseSubject.next({
      ...current,
      show: false
    });
  }
}
