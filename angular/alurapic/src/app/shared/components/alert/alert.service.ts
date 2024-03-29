import { Injectable } from "@angular/core";
import { NavigationStart, Router } from "@angular/router";
import { Observable, Subject } from "rxjs";
import { Alert, AlertType } from "./alert";

@Injectable({ providedIn: 'root'})
export class AlertService {

    alertSubject: Subject<Alert|null> = new Subject<Alert|null>();
    keepAfterRouteChange = false;

    constructor(router: Router) {
        router.events.subscribe((event) => {
            if (event instanceof NavigationStart) {
                if (this.keepAfterRouteChange) {
                    this.keepAfterRouteChange = false;
                } else {
                    this.clearImmediate();
                }
            }
        });
    }

    success(message: string, keepAfterRouteChange: boolean = false): void {
        this.alert(AlertType.SUCCESS, message, keepAfterRouteChange);
    }

    warning(message: string, keepAfterRouteChange: boolean = false): void {
        this.alert(AlertType.WARNING, message, keepAfterRouteChange);
    }

    danger(message: string, keepAfterRouteChange: boolean = false): void {
        this.alert(AlertType.DANGER, message, keepAfterRouteChange);
    }

    info(message: string, keepAfterRouteChange: boolean = false): void {
        this.alert(AlertType.INFO, message, keepAfterRouteChange);
    }

    private alert(alertType: AlertType, message: string, keepAfterRouteChange: boolean): void {

        this.keepAfterRouteChange = keepAfterRouteChange
        this.alertSubject.next(new Alert(alertType, message));
    }

    private clearImmediate(): void {
        this.alertSubject.next(null);
    }

    getAlert(): Observable<Alert|null> {
        return this.alertSubject.asObservable();
    }

}