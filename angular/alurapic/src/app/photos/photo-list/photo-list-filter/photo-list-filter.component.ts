import { Component, EventEmitter, Input, OnDestroy, OnInit, Output } from '@angular/core';
import { debounceTime, Subject } from 'rxjs';

@Component({
    selector: 'app-photo-list-filter',
    templateUrl: './photo-list-filter.component.html',
})
export class PhotoListFilterComponent implements OnInit, OnDestroy {

    @Output()
    onSearch = new EventEmitter<string>();

    @Input()
    value: string = '';

    debounce: Subject<string> = new Subject<string>();

    constructor() { }

    ngOnInit(): void {
        this.debounce
            .pipe(debounceTime(500))
            .subscribe(filter => this.onSearch.emit(filter));
    }

    ngOnDestroy() {
        this.debounce.unsubscribe();
        this.onSearch.unsubscribe();
    }

}
