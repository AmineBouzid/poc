import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { TimeComponent } from './time/time.component';
import { ProfileComponent } from './profile/profile.component';

const routes: Routes = [
  { path: ``, redirectTo: '/profile', pathMatch: 'full' },
  { path: `profile`, component: ProfileComponent },
  { path: `time`, component: TimeComponent }
];



@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
