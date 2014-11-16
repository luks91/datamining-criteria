/*
 * Copyright (C) 2014 Lukasz Szczyglowski, Radoslaw Trzcionkowski
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.github.luks91.data;

import com.github.luks91.data.DatasetContentFactory.IDatasetContent;


abstract class AbstractDatasetContent implements IDatasetContent {
	
	private final int mVerticesAmount;
	
	protected final String mDatasetFilePath;
	protected final String mGroundTruthFilePath;

	public AbstractDatasetContent(String filePath, String groundTruthPath, 
			int verticesAmount) {
		
		mDatasetFilePath = filePath;
		mGroundTruthFilePath = groundTruthPath;
		mVerticesAmount = verticesAmount;
	}
	
	abstract String getDescription();
	
	@Override
	public int getVerticesAmount() {
		return mVerticesAmount;
	}
	
	@Override
	public String getFilePath() {
		return mDatasetFilePath;
	}
	
	@Override
	public String toString() {
		return getDescription();
	}
}
