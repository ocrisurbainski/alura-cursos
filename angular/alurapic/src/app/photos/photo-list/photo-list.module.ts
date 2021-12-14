import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PhotoListComponent } from './photo-list.component';
import { PhotosComponent } from './photos/photos.component';
import { LoadButtonComponent } from './load-button/load-button.component';
import { FilterByDescriptionPipe } from './filter-by-description.pipe';
import { PhotoModule } from '../photo/photo.module';
import { CardModule } from 'src/app/shared/components/card/card.module';
import { PhotoListFilterComponent } from './photo-list-filter/photo-list-filter.component';

@NgModule({
	declarations: [
		PhotoListComponent,
		PhotosComponent,
		LoadButtonComponent,
		FilterByDescriptionPipe,
  		PhotoListFilterComponent,
	],
	imports: [
		CommonModule,
		PhotoModule,
		CardModule,
	]
})
export class PhotoListModule { }
