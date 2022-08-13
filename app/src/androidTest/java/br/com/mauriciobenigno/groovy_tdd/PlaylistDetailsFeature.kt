package br.com.mauriciobenigno.groovy_tdd

import androidx.test.espresso.Espresso
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.rule.ActivityTestRule
import br.com.mauriciobenigno.groovy_tdd.playlist.idlingResource
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertDisplayed
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions.assertNotDisplayed
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test

class PlaylistDetailsFeature : BaseUITest() {


    val mActivityRule = ActivityTestRule(MainActivity::class.java)
        @Rule get

    @Test
    fun displayPlaylistNameAndDetails(){
        navigateToPlaylistDetails(0)

        assertDisplayed("Hard Rock Cafe")

        assertDisplayed("Rock your senses with this timeless signature vibe list. \n\n • Poison \n • You shook me all night \n • Zombie \n • Rock'n Me \n • Thunderstruck \n • I Hate Myself for Loving you \n • Crazy \n • Knockin' on Heavens Door")

    }

    @Test
    fun displaysLoaderWhileFetchingThePlaylist(){
        IdlingRegistry.getInstance().unregister(idlingResource)

        Thread.sleep(6001)
        navigateToPlaylistDetails(0)


        assertDisplayed(R.id.details_loader)

    }

    @Test
    fun hidesLoader() {
        navigateToPlaylistDetails(0)

        assertNotDisplayed(R.id.details_loader)
    }

    @Test
    fun displayErrorMessageWhenNetworksFails(){
        navigateToPlaylistDetails(1)

        assertDisplayed(R.string.generic_error)
    }

    private fun navigateToPlaylistDetails(rowId: Int) {
        Espresso.onView(
            CoreMatchers.allOf(
                ViewMatchers.withId(R.id.playlists_image),
                ViewMatchers.isDescendantOfA(
                    nthChildOf(
                        ViewMatchers.withId(R.id.playlists_list),
                        rowId
                    )
                )
            )
        ).perform(ViewActions.click())
    }

}