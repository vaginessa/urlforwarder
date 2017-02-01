/*
    UrlForwarder makes it possible to use bookmarklets on Android
    Copyright (C) 2017 David Laurell

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.daverix.urlforward;

import net.daverix.urlforward.dagger.FragmentComponentBuilder;
import net.daverix.urlforward.dagger.FragmentKey;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module(subcomponents = {
        LinksFragmentComponent.class,
        FiltersFragmentComponent.class,
        SaveFilterFragmentComponent.class
})
public abstract class FragmentsModule {
    @Binds @IntoMap @FragmentKey(FiltersFragment.class)
    abstract FragmentComponentBuilder bindFiltersFragment(FiltersFragmentComponent.Builder builder);

    @Binds @IntoMap @FragmentKey(LinksFragment.class)
    abstract FragmentComponentBuilder bindLinksFragment(LinksFragmentComponent.Builder builder);

    @Binds @IntoMap @FragmentKey(SaveFilterFragment.class)
    abstract FragmentComponentBuilder bindSaveFiltersFragment(SaveFilterFragmentComponent.Builder builder);
}
