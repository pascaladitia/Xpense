package com.pascal.xpense.ui.screen.onboarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import xpense.sharedui.generated.resources.Res
import xpense.sharedui.generated.resources.label_description_onboarding
import xpense.sharedui.generated.resources.label_get_started
import xpense.sharedui.generated.resources.label_title_onboarding
import xpense.sharedui.generated.resources.logo
import chaintech.videoplayer.host.MediaPlayerHost
import chaintech.videoplayer.model.ScreenResize
import chaintech.videoplayer.model.VideoPlayerConfig
import chaintech.videoplayer.ui.youtube.YouTubePlayerComposable
import com.pascal.xpense.ui.component.button.ButtonComponent
import com.pascal.xpense.ui.component.screenUtils.PagerIndicator
import com.pascal.xpense.ui.component.screenUtils.verticalFadeBackground
import com.pascal.xpense.ui.screen.onboarding.state.LocalOnboardingEvent
import com.pascal.xpense.ui.theme.AppTheme
import com.pascal.xpense.utils.VideoUtils
import kotlinx.coroutines.delay
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier
) {
    val event = LocalOnboardingEvent.current
    val videoList = VideoUtils.getOnboardingVideo()
    val pagerState = rememberPagerState(pageCount = { videoList.size })

    val playerHosts = remember {
        videoList.map { url ->
            MediaPlayerHost(
                mediaUrl = url,
                autoPlay = true,
                isMuted = true,
                isLooping = true,
                headers = null,
                initialVideoFitMode = ScreenResize.FIT
            )
        }
    }

    LaunchedEffect(pagerState.currentPage) {
        playerHosts.forEachIndexed { index, host ->
            if (index == pagerState.currentPage) {
                host.play()
            } else {
                host.pause()
            }
        }
    }

    LaunchedEffect(Unit) {
        while (true) {
            delay(15000)
            if (!pagerState.isScrollInProgress) {
                val nextPage =
                    if (pagerState.currentPage == pagerState.pageCount - 1) 0
                    else pagerState.currentPage + 1
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            YouTubePlayerComposable(
                modifier = Modifier.fillMaxSize(),
                playerHost = playerHosts[page],
                playerConfig = VideoPlayerConfig(
                    showControls = false,
                    isPauseResumeEnabled = false,
                    isSeekBarVisible = false,
                    isDurationVisible = false,
                    isMuteControlEnabled = false,
                    isSpeedControlEnabled = false,
                    isFullScreenEnabled = true,
                    showSubTitlesOptions = false,
                    reelVerticalScrolling = true,
                    enableFullEdgeToEdge = true,
                    enableResumePlayback = true
                )
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .verticalFadeBackground(isTop = true)
                .statusBarsPadding(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .padding(top = 48.dp, bottom = 16.dp)
                    .width(100.dp),
                painter = painterResource(Res.drawable.logo),
                contentDescription = null
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .verticalFadeBackground(isTop = false)
                .padding(24.dp)
        ) {
            Text(
                text = stringResource(Res.string.label_title_onboarding),
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Normal
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(Res.string.label_description_onboarding),
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                ),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))

            PagerIndicator(
                modifier = Modifier.fillMaxWidth(),
                pageCount = videoList.size,
                currentPage = pagerState.currentPage
            )

            Spacer(modifier = Modifier.height(32.dp))

            ButtonComponent(
                text = stringResource(Res.string.label_get_started),
                height = 56.dp,
                onClick = event.onNext
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardingPreview() {
    AppTheme {
        OnboardingScreen()
    }
}
