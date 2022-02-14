import { Component, OnInit } from "@angular/core";
import { LoadingService } from "./loading.service";
import { map, Observable } from "rxjs";

@Component({
    selector: 'app-loading',
    templateUrl: './loading.component.html',
    styleUrls: ['./loading.component.css'],
})
export class LoadingComponent implements OnInit {

    public loading$: Observable<string>;

    constructor(private loadingService: LoadingService) {}

    ngOnInit(): void { 
        this.loading$ = this.loadingService
            .getLoading()
            .pipe(map(loadingType => loadingType.valueOf()))
    }
}